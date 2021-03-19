package cn.leaf.exercise.rpc;


import lombok.RequiredArgsConstructor;
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
@MapperScan("cn.leaf.exercise.repository.mapper")
public class RpcServiceDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(RpcServiceDemoApplication.class, args);
    }


}
