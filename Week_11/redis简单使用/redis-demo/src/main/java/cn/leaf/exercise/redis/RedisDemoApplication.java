package cn.leaf.exercise.redis;

import cn.leaf.exercise.redis.model.MockResource;
import cn.leaf.exercise.redis.pubsub.OrderPubSubListenerService;
import cn.leaf.exercise.redis.service.impl.MockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@RequiredArgsConstructor
public class RedisDemoApplication implements ApplicationRunner {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final OrderPubSubListenerService orderPubSubListenerService;

    @Resource(name = "listener")
    private  Jedis jedis;


    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 订阅
        jedis.subscribe(orderPubSubListenerService, "order");

        // 模拟减库存，并模拟多服务：使用redis分布式锁
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        MockResource resource = new MockResource(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new MockServiceImpl("test" + i, resource, redisTemplate));
        }

    }


}
