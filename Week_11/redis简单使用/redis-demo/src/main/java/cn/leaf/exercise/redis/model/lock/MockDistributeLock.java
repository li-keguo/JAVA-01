package cn.leaf.exercise.redis.model.lock;

import java.util.concurrent.locks.Lock;

/**
 * 模拟分布式锁
 * @author 李克国
 * @date 2021/4/5
 */
public interface MockDistributeLock {

    /**
     * 加锁：若锁暂时被占用，当前线程将阻塞等待，直到获取到锁
     */
    void lock();

    /**
     * 尝试加锁：若获取锁时失败：表示被占用或者其他未知的情况，请在false后指定降级处理
     * @return 是否成功
     */
    boolean tryLock();

    /**
     * 释放锁
     */
    void unlock();

}
