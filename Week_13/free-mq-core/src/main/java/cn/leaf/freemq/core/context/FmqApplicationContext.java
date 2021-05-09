package cn.leaf.freemq.core.context;

import cn.leaf.freemq.core.consumer.FmqConsumer;
import cn.leaf.freemq.core.event.EventManager;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.pool.PoolConnectable;
import cn.leaf.freemq.core.producer.FmqProducer;
import cn.leaf.freemq.core.register.RegisterManager;
import org.springframework.context.Lifecycle;

import java.util.concurrent.ThreadFactory;

/**
 * application context
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 15:23
 */
public interface FmqApplicationContext extends PoolConnectable, Lifecycle {

    /**
     * get message pool hotel california
     *
     * @return message pool
     */
    FmqPool getFmqMessagePool();

    /**
     * get register manager
     *
     * @return register manager
     */
    RegisterManager getRegisterManager();

    /**
     * get current consumer
     *
     * @return consumer
     */
    FmqConsumer getCurrentConsumer();

    /**
     * get current producer
     *
     * @return producer
     */
    FmqProducer getCurrentProducer();

    /**
     * get event manager
     *
     * @return event manager
     */
    EventManager getEventManager();


    /**
     * initialize
     */
    void initialize();

    /**
     * is start
     *
     * @return is start
     */
    boolean isStart();

    /**
     * ThreadFactory
     *
     * @return ThreadFactory
     */
    ThreadFactory threadFactory();
}
