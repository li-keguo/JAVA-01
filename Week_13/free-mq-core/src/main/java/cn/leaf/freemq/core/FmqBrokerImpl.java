package cn.leaf.freemq.core;

import cn.leaf.freemq.core.consumer.DefaultFmqConsumerImpl;
import cn.leaf.freemq.core.consumer.FmqConsumer;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.plug.FmqPlugin;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.producer.FmqProducer;
import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;
import com.sun.xml.internal.ws.util.CompletedFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 李克国
 * @date 2021/5/1
 */
@Slf4j
public class FmqBrokerImpl implements FmqBroker {

    private final String brokerWorkId;

    private final FmqApplicationContext applicationContext;

    private final Map<String, List<FmqConsumer>> consumerPool;

    private final Map<String, FmqProducer> producerPool;

    private final List<FmqPlugin> plugs;

    public FmqBrokerImpl(String brokerWorkId, FmqApplicationContext applicationContext) {
        this.brokerWorkId = brokerWorkId;
        this.applicationContext = applicationContext;
        consumerPool = new ConcurrentHashMap<>();
        producerPool = new ConcurrentHashMap<>();
        plugs = new CopyOnWriteArrayList<>();
    }

    @Override
    public FmqApplicationContext getFmqApplicationContext() {
        return applicationContext;
    }

    @Override
    public boolean sendMessage(FmqDataKey key, FmqMessage<?> fmqMessage) {
        // TODO get producer from pool to load balance
        FmqProducer producer = applicationContext.getCurrentProducer();

        return producer.send(key, fmqMessage);
    }

    @Override
    public boolean addTopic(FmqTopic topic) {
        FmqPool fmqMessagePool = applicationContext.getFmqMessagePool();
        if (fmqMessagePool.contains(topic)) {
            return false;
        }

        boolean result = fmqMessagePool.add(topic);
        if (result) {
            addConsumer(topic);
        }
        return result;
    }

    @Override
    public void addPlug(FmqPlugin plug) {
        plugs.add(plug);
    }

    @Override
    public void setProducer(String key, FmqProducer producer) {
        if (producerPool.containsKey(key)) {
            producerPool.get(key).destroy();
            producerPool.remove(key);
        }
        producerPool.put(key, producer);
    }

    private void addConsumer(FmqTopic topic) {
        FmqConsumer consumer = new DefaultFmqConsumerImpl(applicationContext);
        topic.start();
        consumer.subscribe(topic);
        consumer.start();
        if (consumerPool.containsKey(topic.getKey())) {
            consumerPool.get(topic.getKey()).add(consumer);
        } else {
            CopyOnWriteArrayList<FmqConsumer> consumers = new CopyOnWriteArrayList<>();
            consumers.add(consumer);
            consumerPool.put(topic.getKey(), consumers);
        }
    }

    @Override
    public void shutdown() {
        applicationContext.stop();
        plugs.forEach(p -> p.destroy(this));
        log.info("broker [{}] shutdown", brokerWorkId);
    }

    @Override
    public void run() {
        Assert.notNull(brokerWorkId, "brokerId is not null");
        applicationContext.initialize();

        applicationContext.start();

        log.info("broker [{}] is run ... ...", brokerWorkId);
        plugs.forEach(p -> p.run(this));
        if(applicationContext.isRunning()){
            CountDownLatch countDownLatch = new CountDownLatch(2);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        while (applicationContext.isRunning()){
//
//        }
    }
}
