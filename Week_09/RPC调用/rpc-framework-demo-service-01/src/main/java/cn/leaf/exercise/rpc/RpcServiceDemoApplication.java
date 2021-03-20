package cn.leaf.exercise.rpc;


import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.rpc.api.OrderService;
import cn.leaf.exercise.rpc.core.annotation.RpcScan;
import cn.leaf.exercise.rpc.model.entity.ServiceResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author 李克国
 */
@RequiredArgsConstructor
@SpringBootApplication
@EnableAspectJAutoProxy
@RpcScan(base = "cn.leaf.exercise.service")
@MapperScan("cn.leaf.exercise.repository.mapper")
public class RpcServiceDemoApplication implements ApplicationRunner {

    private final OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(RpcServiceDemoApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServiceResultEntity<XmShoppingOrder> xmShoppingOrderServiceResultEntity = orderService.get("");

    }
}
