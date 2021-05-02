package cn.leaf.freemq.core.producer;

import cn.leaf.freemq.core.pool.PoolConnectable;
import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;

/**
 * producer
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/27 18:47
 */
public interface FmqProducer extends PoolConnectable {


    /**
     * 发送消息
     *
     * @param dataKey dataKey
     * @param message message
     * @return is success
     */
    boolean send(FmqDataKey dataKey, FmqMessage<?> message);

}
