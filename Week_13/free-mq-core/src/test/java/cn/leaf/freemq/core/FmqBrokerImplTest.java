package cn.leaf.freemq.core;

import cn.leaf.freemq.core.context.DefaultFmqApplicationContextImpl;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.event.DefaultEventManager;
import cn.leaf.freemq.core.event.MessagePublishFmqEvent;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.pool.MessageQueue;
import cn.leaf.freemq.core.pool.TopicPoolImpl;
import cn.leaf.freemq.core.register.RegisterManager;
import cn.leaf.freemq.core.register.RegisterManagerImpl;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FmqBrokerImplTest {


    private FmqBroker broker;

    @BeforeEach
    public void before() {
        DefaultEventManager eventManager = new DefaultEventManager();
        RegisterManager registerManager = new RegisterManagerImpl();
        FmqPool fmqPool = new TopicPoolImpl();

        FmqApplicationContext applicationContext = new DefaultFmqApplicationContextImpl(registerManager, eventManager);
        applicationContext.connect(fmqPool);

        broker = new FmqBrokerImpl("broker_01", applicationContext);
        broker.run();
    }

    @Test
    public void testSendMessage() {
        FmqTopic topic = new FmqTopic("test");
        // add topic is success
        assertTrue(broker.addTopic(topic), "broker add topic is fail");

        FmqMessage<String> message = new FmqMessage<>();
        message.setBody("ha ha ha" );
        // add message to topic is success
        assertTrue(broker.sendMessage(topic, message));
        MessageQueue messageQueue = broker.getFmqApplicationContext().getFmqMessagePool().getMessageQueue(topic);
        // topic queue poll message is in line with expectations
//        assertEquals("ha ha ha", messageQueue.poll().getBody());
        // topic queue is null
        assertNull(messageQueue.poll());

    }

    @Test
    public void testSendMessages() {
        FmqTopic topic = new FmqTopic("test");
        // add topic is success
        assertTrue(broker.addTopic(topic), "broker add topic is fail");

        for (int i = 0; i < 1000; i++) {
            FmqMessage<String> message = new FmqMessage<>();
            message.setBody("ha ha ha" + i);
            // add message to topic is success
            assertTrue(broker.sendMessage(topic, message));
        }

    }

}