package cn.leaf.freemq.core.pool;

import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * default
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/27 19:34
 */

public class DefaultTopicQueueImpl implements TopicQueue {

    private final FmqDataKey topic;

    private final LinkedBlockingQueue<FmqMessage<?>> queue;

    public DefaultTopicQueueImpl(FmqDataKey topic) {
        this.topic = topic;
        queue = new LinkedBlockingQueue<>(10000);
    }


    @Override
    public FmqDataKey keyInfo() {
        return topic;
    }

    @Override
    public void push(FmqMessage<?> message) {
        queue.offer(message);
    }

    @Override
    public FmqMessage<?> poll() {
        return queue.poll();
    }

    @Override
    public Integer size() {
        return queue.size();
    }
}
