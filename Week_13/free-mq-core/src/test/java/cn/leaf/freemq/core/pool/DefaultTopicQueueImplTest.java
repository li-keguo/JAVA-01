package cn.leaf.freemq.core.pool;

import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DefaultTopicQueueImplTest {
    private TopicQueue topicQueue;

    @BeforeEach
    public void before() {
        topicQueue = new DefaultTopicQueueImpl(new FmqTopic("test"));
    }

    @Test
    public void topicQueueTest() {
        // add
        topicQueue.push(FmqMessage.builder().body("test message 01").build());
        topicQueue.push(FmqMessage.builder().body("test message 02").build());

        // poll
        assertEquals(topicQueue.poll().getBody(), "test message 01");

        assertEquals(topicQueue.poll().getBody(), "test message 02");

        assertNull(topicQueue.poll(), "assert null");
    }

}