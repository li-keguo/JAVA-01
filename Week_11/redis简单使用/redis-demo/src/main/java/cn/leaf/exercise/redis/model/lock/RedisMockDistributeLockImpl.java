package cn.leaf.exercise.redis.model.lock;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 李克国
 * @date 2021/4/5
 */
@RequiredArgsConstructor
public class RedisMockDistributeLockImpl implements MockDistributeLock {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final Object resourceObject;


    @Override
    public void lock() {
        if (!tryLock()) {
            // sleep
            sleep(redisTemplate.getExpire(getLockKeyString(), TimeUnit.SECONDS) * 1000);
            lock();
        }
    }

    @Override
    public boolean tryLock() {
        String key = getLockKeyString();
        // 锁已存在：加锁失败
        if (redisTemplate.hasKey(key) && redisTemplate.getExpire(key) < 1000) {
            return false;
        }
        redisTemplate.opsForValue().set(key, resourceObject, 10, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void unlock() {
        redisTemplate.delete(getLockKeyString());
    }


    private String getLockKeyString() {
        return "mockLockKey";
    }

    @SneakyThrows
    private void sleep(long time) {
        Thread.sleep(time);
    }
}
