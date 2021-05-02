package cn.leaf.freemq.core.pool;

import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqTopic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/27 19:25
 */
public class TopicPoolImpl implements TopicPool {

    private final Map<String, TopicQueue> POOL = new ConcurrentHashMap<>(64);

    public final String TOPIC_PREFIX = "topic_";


    @Override
    public boolean add(FmqDataKey key) {
        if (POOL.containsKey(key(key))) {
            return false;
        }
        POOL.put(key(key), new DefaultTopicQueueImpl(key));
        return true;
    }

    @Override
    public boolean contains(FmqDataKey key) {
        return POOL.containsKey(key(key));
    }

    @Override
    public MessageQueue getMessageQueue(FmqDataKey key) {
        return POOL.get(key(key));
    }

    private String key(FmqDataKey key) {
        return TOPIC_PREFIX + key.getKey();
    }

    private TopicQueue topicQueue(FmqTopic topic) {
        return new DefaultTopicQueueImpl(topic);
    }
}
