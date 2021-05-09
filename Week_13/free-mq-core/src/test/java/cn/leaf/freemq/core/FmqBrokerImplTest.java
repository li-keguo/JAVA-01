package cn.leaf.freemq.core;

import cn.leaf.freemq.core.context.DefaultFmqApplicationContextImpl;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.event.DefaultEventManager;
import cn.leaf.freemq.core.event.MessagePublishFmqEvent;
import cn.leaf.freemq.core.plug.command_line_plugin.CommandLineManagementFmqPluginImpl;
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FmqBrokerImplTest {


    private FmqBroker broker;

    @BeforeEach
    public void before() {
        broker = new FmqBrokerImpl("broker_01", DefaultFmqApplicationContextImpl.defaultFmqApplicationContext());
        broker.run();
    }

    @Test
    public void testSendMessage() {
        FmqTopic topic = new FmqTopic("test");
        // add topic is success
        assertTrue(broker.addTopic(topic), "broker add topic is fail");

        FmqMessage<String> message = new FmqMessage<>();
        message.setBody("ha ha ha");
        // add message to topic is success
        assertTrue(broker.sendMessage(topic, message));
        assertTrue(broker.sendMessage(topic, message));
        assertTrue(broker.sendMessage(topic, message));
//        MessageQueue messageQueue = broker.getFmqApplicationContext().getFmqMessagePool().getMessageQueue(topic);
        // topic queue poll message is in line with expectations heal the world something just like this the spectre

//        assertEquals("ha ha ha", messageQueue.poll().getBody());
        // topic queue is null
//        assertNull(messageQueue.poll());

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

    public static void main(String[] args) throws IOException {
        FmqBroker broker;
        broker = new FmqBrokerImpl("broker_01", DefaultFmqApplicationContextImpl.defaultFmqApplicationContext());
        broker.addPlug(new CommandLineManagementFmqPluginImpl());
        broker.run();
//        FmqTopic topic = new FmqTopic("test");
//        // add topic is success
//        assertTrue(broker.addTopic(topic), "broker add topic is fail");
//
//        while (true) {
//            char c = (char) System.in.read();
//            if (c > 20) {
//                FmqMessage<String> message = new FmqMessage<>();
//                message.setBody(String.format("message command code is [%s] ", c));
//                // add message to topic is success
//                assertTrue(broker.sendMessage(topic, message));
//            }
//
//            if (c == 'q' || c == 'e') break;
//        }
//        broker.shutdown();
    }


}