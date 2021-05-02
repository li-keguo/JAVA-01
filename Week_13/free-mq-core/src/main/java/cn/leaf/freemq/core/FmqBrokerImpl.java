package cn.leaf.freemq.core;

import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.producer.FmqProducer;
import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author 李克国
 * @date 2021/5/1
 */
@Slf4j
@RequiredArgsConstructor
public class FmqBrokerImpl implements FmqBroker {

    private final String brokerWorkId;

    private final FmqApplicationContext applicationContext;

    @Override
    public FmqApplicationContext getFmqApplicationContext() {
        return applicationContext;
    }

    @Override
    public boolean sendMessage(FmqDataKey key, FmqMessage<?> fmqMessage) {
        FmqProducer producer = applicationContext.getCurrentProducer();

        return producer.send(key, fmqMessage);
    }

    @Override
    public boolean addTopic(FmqTopic topic) {
        FmqPool fmqMessagePool = applicationContext.getFmqMessagePool();
        if (fmqMessagePool.contains(topic)) {
            return false;
        }
        return fmqMessagePool.add(topic);
    }

    @Override
    public void run() {
        Assert.notNull(brokerWorkId,"brokerId is not null");
        applicationContext.initialize();

        log.info("broker [{}] is run ... ...", brokerWorkId);
    }
}
