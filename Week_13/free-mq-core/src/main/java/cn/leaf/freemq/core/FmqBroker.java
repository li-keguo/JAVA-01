package cn.leaf.freemq.core;


import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.plug.FmqPlugin;
import cn.leaf.freemq.core.producer.FmqProducer;
import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;

/**
 * broker
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/26 16:26
 */
public interface FmqBroker extends Runnable {


    /**
     * get application context
     *
     * @return application context
     */
    FmqApplicationContext getFmqApplicationContext();


    /**
     * send message
     *
     * @param key        key
     * @param fmqMessage message
     * @return is success
     */
    boolean sendMessage(FmqDataKey key, FmqMessage<?> fmqMessage);

    /**
     * add topic
     *
     * @param topic topic
     * @return is success
     */
    boolean addTopic(FmqTopic topic);


    /**
     * add plug
     *
     * @param plug plug
     */
    void addPlug(FmqPlugin plug);

    /**
     * set producer
     *
     * @param key      key
     * @param producer producer
     */
    void setProducer(String key, FmqProducer producer);


    /**
     * shutdown
     */
    void shutdown();
}
