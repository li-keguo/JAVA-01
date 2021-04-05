package cn.leaf.exercise.redis.service.impl;

import cn.leaf.exercise.redis.model.MockResource;
import cn.leaf.exercise.redis.model.lock.MockDistributeLock;
import cn.leaf.exercise.redis.model.lock.RedisMockDistributeLockImpl;
import cn.leaf.exercise.redis.service.MockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Random;

/**
 * @author 李克国
 * @date 2021/4/5
 */
@Slf4j
@RequiredArgsConstructor
public class MockServiceImpl implements MockService {

    private final String name;

    private final MockResource mockResource;

    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void run() {
//        Thread.currentThread().setName(name + "-thread");
        log.info("{} service start ... ...", name);
        MockDistributeLock redisMockDistributeLock = new RedisMockDistributeLockImpl(redisTemplate, mockResource);

        if (redisMockDistributeLock.tryLock()) {
            try {
                log.info("{} 获取到加锁成功，执行前 i = {}", name, mockResource.getStock());
                // 假设我执行比较耗时
                mockSleep();
                mockResource.setStock(mockResource.getStock() - 1);
                log.info("{} 执行完成,执行后 i = {}", name, mockResource.getStock());
            } finally {
                redisMockDistributeLock.unlock();
                log.info("{} 释放锁 ", name);
            }

        } else {
            log.info("{} 尝试加锁失败", name);
        }
    }

    private void mockSleep() {
        try {
            Random random = new Random();
            int i = random.nextInt(10) * 1_000;
//            log.info("sleep {}", i);
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
