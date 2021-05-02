package cn.leaf.freemq.core.producer;

import cn.leaf.freemq.common.exception.FmqPoolNotFoundExeption;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.event.EventManager;
import cn.leaf.freemq.core.event.MessagePublishFmqEvent;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.pool.MessageQueue;
import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * producer
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 11:12
 */
@Slf4j
@RequiredArgsConstructor
public class FmqTopicProducer implements FmqProducer {

    private final FmqApplicationContext applicationContext;

    private FmqPool pool;

    @Override
    public void connect(FmqPool pool) {
        this.pool = pool;
    }

    @Override
    public boolean send(FmqDataKey dataKey, FmqMessage<?> message) {
        if (pool == null) {
            throw new FmqPoolNotFoundExeption("message queue pool is not connection ");
        }
        // dataKey is not found
        if (!pool.contains(dataKey)) {
            log.warn("producer send message to topic [{}] , but it not found topic", dataKey.getKey());
            return false;
        }
        MessageQueue messageQueue = pool.getMessageQueue(dataKey);

        messageQueue.push(message);

        // TODO 原子性问题

        // TODO publish event
        EventManager eventManager = applicationContext.getEventManager();

        eventManager.publish(new MessagePublishFmqEvent(applicationContext, dataKey));
        log.info("producer send message to topic [{}]", dataKey.getKey());
        return true;
    }
}
