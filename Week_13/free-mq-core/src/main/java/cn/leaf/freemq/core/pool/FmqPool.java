package cn.leaf.freemq.core.pool;

import cn.leaf.freemq.model.FmqDataKey;

/**
 * pool
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 11:10
 */
public interface FmqPool {

    /**
     * 是否存在
     *
     * @param key key
     * @return is contains
     */
    boolean contains(FmqDataKey key);

    /**
     * add key
     *
     * @param key key
     * @return is success
     */
    boolean add(FmqDataKey key);

    /**
     * get key
     *
     * @param keyId keyId
     * @return key
     */
    FmqDataKey getDataKey(String keyId);

    /**
     * 获取消息队列
     *
     * @param key key
     * @return queue
     */
    MessageQueue getMessageQueue(FmqDataKey key);
}
