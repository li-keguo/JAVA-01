package cn.leaf.exercise.activemq;

import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;
import java.util.Map;

/**
 * @author 李克国
 */
@EnableJms
@SpringBootApplication
@RequiredArgsConstructor
@RestController
public class ActivemqApplication implements ApplicationRunner {

    private final JmsMessagingTemplate jmsMessagingTemplate;


    public static void main(String[] args) {
        SpringApplication.run(ActivemqApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ActiveMQQueue queue = new ActiveMQQueue("test-queue");
        ActiveMQTopic topic = new ActiveMQTopic("test-topic");

        for (int i = 0; i < 10; i++) {
            jmsMessagingTemplate.convertAndSend(queue, "hello queue " + i);
            jmsMessagingTemplate.convertAndSend(topic, "hello topic " + i);
        }
    }

    @RequestMapping("activemq//topic/send/{topic}/{message}")
    public ResponseEntity<String> sendByTopic(@PathVariable("topic") String topic, @PathVariable("message") String message) {
        jmsMessagingTemplate.convertAndSend(new ActiveMQTopic(topic), message);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping("activemq/queue/send/{queue}/{message}")
    public ResponseEntity<String> sendByQueue(@PathVariable("queue") String topic, @PathVariable("message") String message) {
        jmsMessagingTemplate.convertAndSend(topic, message);
        return ResponseEntity.ok("ok");
    }

    /**
     * 监听queue <br>
     * 1.Point-to-Point
     * 2.Queue数据默认会在mq服务器上以文件形式保存，比如Active MQ一般保存在$AMQ_HOME\data\kr-store\data下面。也可以配置成DB存储。
     * 3.Queue保证每条数据都能被receiver接收。
     * 4.Sender发送消息到目标Queue，receiver可以异步接收这个Queue上的消息。Queue上的消息如果暂时没有receiver来取，也不会丢失。
     * 5.一对一的消息发布接收策略，一个sender发送的消息，只能有一个receiver接收。receiver接收完后，通知mq服务器已接收，mq服务器对queue里的消息采取删除或其他操作。
     *
     * @param message message
     */
    @JmsListener(destination = "test-queue", containerFactory = "queueListenerContainerFactory")
    public void readActiveQueue(String message) {
        System.out.println("test-queue 接受到：" + message);
    }

    /**
     * 监听topic <br>
     * 1.Publish  Subscribe messaging 发布订阅消息
     * 2.topic数据默认不落地，是无状态的。
     * 3.并不保证publisher发布的每条数据，Subscriber都能接受到
     * 4.一般来说publisher发布消息到某一个topic时，只有正在监听该topic地址的sub能够接收到消息；如果没有sub在监听，该topic就丢失了。
     * 5.一对多的消息发布接收策略，监听同一个topic地址的多个sub都能收到publisher发送的消息。Sub接收完通知mq服务器
     *
     * @param message message
     */
    @JmsListener(destination = "test-topic", containerFactory = "topicListenerContainerFactory")
    public void readActiveTopic(String message) {
        System.out.println("test-topic 接受到：" + message);
    }


    @Bean(name = "queueListenerContainerFactory")
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> queueListenerContailerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean(name = "topicListenerContainerFactory")
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> topicListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
