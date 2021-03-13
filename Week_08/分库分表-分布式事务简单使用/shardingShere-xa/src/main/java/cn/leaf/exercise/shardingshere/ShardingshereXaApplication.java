package cn.leaf.exercise.shardingshere;

import cn.hutool.core.util.RandomUtil;
import cn.leaf.exercise.model.po.TOrder;
import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.repository.mapper.TOrderMapper;
import cn.leaf.exercise.repository.mapper.XmShoppingOrderMapper;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan("cn.leaf.exercise.repository.mapper")
public class ShardingshereXaApplication implements ApplicationRunner {

    private final XmShoppingOrderMapper orderMapper;
    private final TOrderMapper tOrderMapper;

    public static void main(String[] args) {
        SpringApplication.run(ShardingshereXaApplication.class, args);
    }

    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(ApplicationArguments args) throws Exception {

        orderMapper.insert(XmShoppingOrder.builder()
                .orderId("test_xa")
                .consumerId(1)
                .consumerPhone("100")
                .shippingAddressId(1)
                .addressSnapshot("测试数据")
                .build());
        System.out.println(orderMapper.selectByPrimaryKey("test_xa"));
//        TOrder condition = TOrder.builder().id(4).orderNo("1").userId(2).build();
//        tOrderMapper.delete(condition);
//        tOrderMapper.insert(condition);
//
//        List<TOrder> tOrders = tOrderMapper.select(condition);
//        tOrders.forEach(System.out::println);
    }

    /**
     * 模拟大量订单插入
     */
    public void mockLotOfOrder() {
        List<Integer> mockUsers = Stream.of(1, 2, 4, 5, 7, 8, 9, 34, 23, 44, 56, 78, 567, 234, 33).collect(Collectors.toList());

        mockUsers.forEach(uId -> {
            // 每人下单100_000
            for (int i = 0; i < 100_000; i++) {
                orderMapper.insert(XmShoppingOrder.builder()
                        .orderId("test_" + uId + i)
                        .consumerId(uId)
                        .consumerPhone(RandomUtil.randomNumbers(11))
                        .shippingAddressId(uId + 1024)
                        .addressSnapshot("用户" + uId + "下单测试数据")
                        .build());
            }
        });
    }
}
