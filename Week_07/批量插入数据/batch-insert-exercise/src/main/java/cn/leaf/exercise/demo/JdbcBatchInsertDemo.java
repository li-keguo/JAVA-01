package cn.leaf.exercise.demo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.leaf.exercise.repository.mapper.XmShoppingOrderMapper;
import cn.leaf.exercise.model.po.XmShoppingOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * 批量插入数据 ：插入1 000 000 订单数据 <br>
 * 目标:干到10s
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/1 19:43
 * @description 批量插入数据
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JdbcBatchInsertDemo {

    private final JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;

    private final XmShoppingOrderMapper orderMapper;

    private final String machineCode = "020378";
    private final int[] types = {Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR};
    String insertSql = "INSERT INTO xm_shopping_order (order_id, consumer_id, consumer_phone, shipping_address_id, address_snapshot,\n" +
            "                                        order_status, create_time, update_time)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, now(), now())";
    int CRITICAL_VALUE = 1_000_000;

    private List<Object[]> valuesObjects;
    private List<XmShoppingOrder> orders;

    @PostConstruct
    public void initialize() {
        valuesObjects = new ArrayList<>(CRITICAL_VALUE);
        orders = new ArrayList<>(CRITICAL_VALUE);
        for (int i = 0; i < CRITICAL_VALUE; i++) {
            orders.add(XmShoppingOrder.builder()
                    .orderId(machineCode + RandomUtil.randomString(6) + i)
                    .consumerId(1)
                    .consumerPhone("1818888" + RandomUtil.randomNumbers(4))
                    .shippingAddressId(1)
                    .addressSnapshot("测试产品")
                    .orderStatus("未支付")
                    .build());
            valuesObjects.add(new Object[]{machineCode + RandomUtil.randomString(6) + i, 1, "1818888" + RandomUtil.randomNumbers(4), 1, "测试产品" + i, "未支付"});
        }
    }

    /**
     * jdbc template batchUpdate 插入 1 000 000 订单记录
     */
    public void jdbcTemplateInsertDemo() {
        log.info("JdbcTemplate 批处理：插入开始");
        long startTime = System.currentTimeMillis();
        int[] ints = jdbcTemplate.batchUpdate(insertSql, valuesObjects, types);
        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("JdbcTemplate 批处理：插入{}订单数据消耗时长为{}}ms ({}}s) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);
    }

    /**
     * 原始 jdbc 批处理插入 插入 1 000 000 订单记录
     *
     * @throws SQLException sql exception
     */
    public void primitiveJdbcInsertDemo() throws SQLException {

        log.info("primitiveJdbc 批处理：插入开始");
        long startTime = System.currentTimeMillis();
        jdbcBatchInsert(valuesObjects);
        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("primitiveJdbc 批处理：插入{}}订单数据消耗时长为{}}ms ({}}s) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);
    }

    /**
     * 原始 jdbc 批处理插入 插入 1 000 000 订单记录 （10线程并行插入）
     *
     * @throws InterruptedException InterruptedException
     */
    public void parallelPrimitiveJdbcInsertDemo() throws InterruptedException {
        List<List<Object[]>> valuesObjectsSplit = CollectionUtil.split(valuesObjects, 100000);
        parallel(this::jdbcBatchInsert, valuesObjectsSplit, "primitiveJdbc 并行插入", 10);
    }

    public void mybatisInset() {
        log.info("mybatis开始");
        long startTime = System.currentTimeMillis();

        List<List<XmShoppingOrder>> split = CollectionUtil.split(orders, 10000);
        for (List<XmShoppingOrder> orderList : split) {
            orderMapper.inserts(orderList);
        }

        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("myBatis 但线程  批处理：插入{}订单数据消耗时长为{}ms ({}s) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);


    }


    public void mybatisParallelInsertDemo() throws InterruptedException {
        List<List<XmShoppingOrder>> split = CollectionUtil.split(orders, CRITICAL_VALUE / 10);
        parallel(list -> {
            int count = 0;
            for (List<XmShoppingOrder> xmShoppingOrders : CollectionUtil.split(list, 1000)) {
                orderMapper.inserts(xmShoppingOrders);
                orderMapper.flush();
                log.debug("第{}批插入完成", count++);
            }
            log.info("插入完成");
        }, split, "mybatis 并行插入", 10);
    }

    public <T> void parallel(Consumer<List<T>> consumer, List<List<T>> parallelData, String name, int processSize) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(processSize);
        CountDownLatch endLatch = new CountDownLatch(processSize);
        long startTime = System.currentTimeMillis();
        log.info("{} 多线程 批处理：插入开始", name);
        for (List<T> objects : parallelData) {
            executorService.submit(() -> {
                consumer.accept(objects);
                endLatch.countDown();
                log.info("{} 处理完毕", name);
            });
        }
        endLatch.await();
        executorService.shutdown();
        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("{} {}个线程并行处理：处理{}数据消耗时长为{}ms ({}s) ", name, processSize, CRITICAL_VALUE, consumeTime, consumeTime / 1000);

    }

    private void jdbcBatchInsert(List<Object[]> valuesObjects) {
        jdbcBatchInsert(valuesObjects, 1000);
    }

    private void jdbcBatchInsert(List<Object[]> valuesObjects, int batchSizze) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql)) {
            for (int i = 0; i < valuesObjects.size(); i++) {
                Object[] objects = valuesObjects.get(i);
                for (int i1 = 0; i1 < objects.length; i1++) {
                    statement.setObject(i1 + 1, objects[i1], types[i1]);
                }
                statement.addBatch();
                if (i % batchSizze == 0) {
                    statement.executeBatch();
                    statement.clearBatch();
                    log.debug("第{}批插入完成", i / batchSizze);
                }
            }
            statement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
