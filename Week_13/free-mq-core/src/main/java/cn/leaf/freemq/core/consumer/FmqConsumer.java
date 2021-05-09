package cn.leaf.freemq.core.consumer;

import cn.leaf.freemq.core.pool.PoolConnectable;
import cn.leaf.freemq.model.FmqDataKey;

/**
 * consumer
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/27 18:46
 */
public interface FmqConsumer extends PoolConnectable {

    /**
     * current key
     *
     * @return key
     */
    FmqDataKey currentDataKey();

    /**
     * start
     */
    void start();

    /**
     * subscribe key message
     *
     * @param key key
     */
    void subscribe(FmqDataKey key);


}
