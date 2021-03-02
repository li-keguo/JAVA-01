package cn.leaf.exercise.exercise.jdbc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import cn.leaf.exercise.exercise.OrderMapper;
import cn.leaf.exercise.exercise.po.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private final OrderMapper orderMapper;

    private final String machineCode = "020378";
    private final int[] types = {Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR};
    String insertSql = "INSERT INTO xm_shopping_order (order_id, consumer_id, consumer_phone, shipping_address_id, address_snapshot,\n" +
            "                                        order_status, create_time, update_time)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, now(), now())";
    int CRITICAL_VALUE = 1_000_000;

    private List<Object[]> valuesObjects;
    List<Order> orders;

    @PostConstruct
    public void initialize() {
        valuesObjects = new ArrayList<>(CRITICAL_VALUE);
        for (int i = 0; i < CRITICAL_VALUE; i++) {
            orders.add(new Order(machineCode + RandomUtil.randomString(6) + i, 1, "1818888" + RandomUtil.randomNumbers(4), 1, "测试产品" + i, "未支付"));
            valuesObjects.add(new Object[]{machineCode + RandomUtil.randomString(6) + i, 1, "1818888" + RandomUtil.randomNumbers(4), 1, "测试产品" + i, "未支付"});
        }
    }

    /**
     * jdbc template batchUpdate 插入 1 000 000 订单记录
     */
    public void jdbcTemplateInsertDemo() {
        System.out.println("JdbcTemplate 批处理：插入开始");
        long startTime = System.currentTimeMillis();
        int[] ints = jdbcTemplate.batchUpdate(insertSql, valuesObjects, types);
        long consumeTime = System.currentTimeMillis() - startTime;
        System.out.printf("JdbcTemplate 批处理：插入%d订单数据消耗时长为%dms (%ds) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);
    }

    /**
     * 原始 jdbc 批处理插入 插入 1 000 000 订单记录
     *
     * @throws SQLException sql exception
     */
    public void primitiveJdbcInsertDemo() throws SQLException {

        System.out.println("primitiveJdbc 批处理：插入开始");
        long startTime = System.currentTimeMillis();
        jdbcBatchInsert(valuesObjects);
        long consumeTime = System.currentTimeMillis() - startTime;
        System.out.printf("primitiveJdbc 批处理：插入%d订单数据消耗时长为%dms (%ds) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);
    }

    /**
     * 原始 jdbc 批处理插入 插入 1 000 000 订单记录 （10线程并行插入）
     *
     * @throws SQLException sql exception
     */
    public void parallelPrimitiveJdbcInsertDemo() throws SQLException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch endLatch = new CountDownLatch(10);
        log.info("primitiveJdbc 多线程 批处理：插入开始");
        long startTime = System.currentTimeMillis();
        List<List<Object[]>> valuesObjectsSplit = CollectionUtil.split(valuesObjects, 10000);
        for (List<Object[]> objects : valuesObjectsSplit) {
            executorService.submit(() -> {
                jdbcBatchInsert(objects);
                endLatch.countDown();
            });
        }
        endLatch.await();
        executorService.shutdown();
        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("primitiveJdbc 多线程  批处理：插入{}订单数据消耗时长为{}ms ({}s) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);
    }

    public void mybatisInset() {
        log.info("mybatis开始");
        long startTime = System.currentTimeMillis();

        List<List<Order>> split = CollectionUtil.split(orders, 10000);
        for (List<Order> orderList : split) {
            orderMapper.inserts(orderList);
        }

        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("myBatis 但线程  批处理：插入{}订单数据消耗时长为{}ms ({}s) ", CRITICAL_VALUE, consumeTime, consumeTime / 1000);


    }


    public void mybatisParallelInsertDemo(int poolSize) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        CountDownLatch endLatch = new CountDownLatch(poolSize);

        long startTime = System.currentTimeMillis();
        System.out.println("JdbcTemplate 多线程 批处理：插入开始");
        List<List<Order>> split = CollectionUtil.split(orders, CRITICAL_VALUE / poolSize);
        for (List<Order> objects : split) {
            executorService.submit(() -> {
                orderMapper.inserts(objects);
                endLatch.countDown();
            });
        }
        endLatch.await();
        executorService.shutdown();
        long consumeTime = System.currentTimeMillis() - startTime;
        log.info("Mybatis {}线程  批处理：插入{}订单数据消耗时长为{}ms ({}s) ", poolSize, CRITICAL_VALUE, consumeTime, consumeTime / 1000);

    }

    private void jdbcBatchInsert(List<Object[]> valuesObjects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql);
        ) {

            for (int i = 0; i < valuesObjects.size(); i++) {
                Object[] objects = valuesObjects.get(i);
                for (int i1 = 0; i1 < objects.length; i1++) {
                    statement.setObject(i1 + 1, objects[i1], types[i1]);
                }
                statement.addBatch();
                if (i % 1000 == 0) {
                    statement.executeBatch();
                    statement.clearBatch();
                    log.debug("第{}批插入完成", i / 1000);
                }
            }
            statement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
