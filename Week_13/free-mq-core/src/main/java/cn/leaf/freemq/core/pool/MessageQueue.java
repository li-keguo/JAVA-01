package cn.leaf.freemq.core.pool;

import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;

/**
 * message queue
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 14:39
 */
public interface MessageQueue {


    /**
     * topic info
     *
     * @return topic
     */
    FmqDataKey keyInfo();


    /**
     * 添加消息
     *
     * @param message message
     */
    void push(FmqMessage<?> message);

    /**
     * 尾部消费
     *
     * @return message
     */
    FmqMessage<?> poll();
}
