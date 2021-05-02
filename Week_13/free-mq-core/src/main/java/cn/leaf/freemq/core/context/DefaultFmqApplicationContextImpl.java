package cn.leaf.freemq.core.context;

import cn.leaf.freemq.core.consumer.DefaultFmqConsumerImpl;
import cn.leaf.freemq.core.consumer.FmqConsumer;
import cn.leaf.freemq.core.event.EventListenerImpl;
import cn.leaf.freemq.core.event.EventManager;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.producer.FmqProducer;
import cn.leaf.freemq.core.producer.FmqTopicProducer;
import cn.leaf.freemq.core.register.RegisterManager;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

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
    public void connect(FmqPool pool) {
        Assert.notNull(pool, "connect fmq pool is not null");
        fmqPool = pool;
    }
}
