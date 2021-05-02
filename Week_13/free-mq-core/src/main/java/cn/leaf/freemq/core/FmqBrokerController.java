package cn.leaf.freemq.core;

import cn.leaf.freemq.core.context.DefaultFmqApplicationContextImpl;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.event.DefaultEventManager;
import cn.leaf.freemq.core.pool.DefaultTopicQueueImpl;
import cn.leaf.freemq.core.pool.TopicPool;
import cn.leaf.freemq.core.pool.TopicPoolImpl;
import cn.leaf.freemq.core.register.RegisterManager;
import cn.leaf.freemq.core.register.RegisterManagerImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * bootstrap
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/26 18:58
 */

public class FmqBrokerController {

    private final TopicPool TOPIC_POOL = new TopicPoolImpl();

    private final Map<String, FmqBroker> BROKER_POOL = new ConcurrentHashMap<>();

//    private ThreadPoolExecutor threadPoolExecutor =

    public void initialize() {
        // reader config
        // init component
        DefaultEventManager eventManager = new DefaultEventManager();
        RegisterManager registerManager = new RegisterManagerImpl();
        FmqApplicationContext applicationContext = new DefaultFmqApplicationContextImpl(registerManager, eventManager);
        applicationContext.connect(TOPIC_POOL);
        for (int i = 0; i < 10; i++) {
            BROKER_POOL.put("broker_" + i, new FmqBrokerImpl("broker_" + i, applicationContext));
        }
    }

    public void start() {

        BROKER_POOL.forEach((brokerId, broker) -> {
            broker.run();
        });
    }

}
