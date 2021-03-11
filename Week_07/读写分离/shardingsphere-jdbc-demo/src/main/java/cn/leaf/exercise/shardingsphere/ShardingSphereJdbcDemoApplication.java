package cn.leaf.exercise.shardingsphere;

import cn.leaf.exercise.shardingsphere.mapper.XmShoppingOrderMapper;
import cn.leaf.exercise.shardingsphere.po.XmShoppingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author 李克国
 */
@RequiredArgsConstructor
@SpringBootApplication
public class ShardingSphereJdbcDemoApplication implements ApplicationRunner {

    private final XmShoppingOrderMapper orderMapper;


    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereJdbcDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // insert into  master
        for (int i = 0; i < 10; i++) {
            orderMapper.insert(XmShoppingOrder.builder()
                    .orderId("test-1" + i + System.currentTimeMillis())
                    .consumerId(1)
                    .shippingAddressId(1)
                    .addressSnapshot("test" + i)
                    .consumerPhone("1818888000" + i)
                    .build());
        }
        // select slave
        XmShoppingOrder condition = new XmShoppingOrder();
        condition.setOrderId("test-11");
        List<XmShoppingOrder> select = orderMapper.select(condition);
        select.forEach(System.out::println);
    }
}
