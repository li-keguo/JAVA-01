package cn.leaf.freemq.core.pool;

import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopicPoolImplTest {

    private FmqDataKey topic;
    private TopicPool topicPool;

    @BeforeEach
    public void before() {
        topic = new FmqTopic("test");
        topicPool = new TopicPoolImpl();
        topicPool.add(topic);
        topicPool.add(new FmqTopic("test1"));
    }


    @Test
    void add() {
        assertFalse(topicPool.add(topic));
        assertFalse(topicPool.add(new FmqTopic("test1")));
        assertTrue(topicPool.add(new FmqTopic("test2")));
    }

    @Test
    void contains() {
        assertTrue(topicPool.contains(topic));
        assertTrue(topicPool.contains(new FmqTopic("test1")));
        assertFalse(topicPool.contains(new FmqTopic("test2")));
    }

    @Test
    void getMessageQueue() {
        MessageQueue messageQueue1 = topicPool.getMessageQueue(topic);
        MessageQueue messageQueue2 = topicPool.getMessageQueue(new FmqTopic("test"));
        assertSame(messageQueue1, messageQueue2);

    }
}