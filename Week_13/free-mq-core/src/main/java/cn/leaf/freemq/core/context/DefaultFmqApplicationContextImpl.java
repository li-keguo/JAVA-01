package cn.leaf.freemq.core.context;

import cn.leaf.freemq.core.consumer.DefaultFmqConsumerImpl;
import cn.leaf.freemq.core.consumer.FmqConsumer;
import cn.leaf.freemq.core.event.DefaultEventManager;
import cn.leaf.freemq.core.event.EventListenerImpl;
import cn.leaf.freemq.core.event.EventManager;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.pool.TopicPoolImpl;
import cn.leaf.freemq.core.producer.FmqProducer;
import cn.leaf.freemq.core.producer.FmqTopicProducer;
import cn.leaf.freemq.core.register.RegisterManager;
import cn.leaf.freemq.core.register.RegisterManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * default
 *
 * @author 李克国
 * @date 2021/5/1
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultFmqApplicationContextImpl implements FmqApplicationContext {

    private FmqPool fmqPool;

    private final RegisterManager registerManager;
    @Setter
    private FmqProducer producer;
    @Setter
    private FmqConsumer consumer;

    private final EventManager eventManager;

    private volatile boolean isStart = false;

    public static FmqApplicationContext defaultFmqApplicationContext(){
        DefaultEventManager eventManager = new DefaultEventManager();
        RegisterManager registerManager = new RegisterManagerImpl();
        FmqPool fmqPool = new TopicPoolImpl();

        FmqApplicationContext applicationContext = new DefaultFmqApplicationContextImpl(registerManager, eventManager);
        applicationContext.connect(fmqPool);
        return applicationContext;
    }

    @Override
    public FmqPool getFmqMessagePool() {
        return fmqPool;
    }

    @Override
    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    @Override
    public FmqConsumer getCurrentConsumer() {
        return consumer;
    }

    @Override
    public FmqProducer getCurrentProducer() {
        return producer;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public void initialize() {
        Assert.notNull(fmqPool, "connect fmq pool is not null, initialize fail");
//        Assert.notNull(producer, "connect producer is not null, initialize fail");
//        Assert.notNull(consumer, "connect consumer is not null, initialize fail");
        producer = new FmqTopicProducer(this);
        consumer = new DefaultFmqConsumerImpl(this);
        producer.connect(fmqPool);
        consumer.connect(fmqPool);
        eventManager.registerEventListener(new EventListenerImpl());

    }

    @Override
    public boolean isStart() {
        return isStart;
    }

    @Override
    public ThreadFactory threadFactory() {
        return Executors.defaultThreadFactory();
    }

    @Override
    public void connect(FmqPool pool) {
        Assert.notNull(pool, "connect fmq pool is not null");
        fmqPool = pool;
    }

    @Override
    public void start() {
        isStart = true;
    }

    @Override
    public void stop() {
        isStart = false;
    }

    @Override
    public boolean isRunning() {
        return isStart;
    }
}
