package cn.leaf.freemq.core.consumer;

import cn.leaf.freemq.common.exception.FmqDataKeyNotFoundException;
import cn.leaf.freemq.common.exception.FmqPoolNotFoundExeption;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.pool.MessageQueue;
import cn.leaf.freemq.core.register.ClientListenerConnection;
import cn.leaf.freemq.core.register.RegisterManager;
import cn.leaf.freemq.model.FmqDataKey;
import cn.leaf.freemq.model.FmqMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * default
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 15:07
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultFmqConsumerImpl implements FmqConsumer {

    private final FmqApplicationContext applicationContext;

    private FmqPool pool;

    @Setter
    private volatile FmqDataKey currentDataKey;

    @Override
    public FmqDataKey currentDataKey() {
        return currentDataKey;
    }

    @Override
    public void start() {
        if (currentDataKey == null || !currentDataKey.isRunning()) {
            throw new IllegalArgumentException("consumer start is fail, listen to DataKey is not found or not subscribe ");
        }
        MessageQueue messageQueue = getMessageQueueByKey(currentDataKey);
        applicationContext.threadFactory().newThread(() -> {
            log.info("consumer listen to dataKey [{}] running", currentDataKey.getKey());
            while (applicationContext.isStart() && currentDataKey.isRunning()) {
                poll(currentDataKey, messageQueue);
            }
            log.info("consumer listen to dataKey [{}] shutdown", currentDataKey.getKey());
        }).start();
    }

    @Override
    public void subscribe(FmqDataKey key) {
        MessageQueue queueByKey = getMessageQueueByKey(key);
        currentDataKey = queueByKey.keyInfo();
        key.start();
        log.info("the dataKey [{}] is subscribed", key);
    }

    private MessageQueue getMessageQueueByKey(FmqDataKey key) {
        if (pool == null && applicationContext.getFmqMessagePool() == null) {
            throw new FmqPoolNotFoundExeption("message queue pool is not connection ");
        }
        if (pool == null) {
            // auto connect
            pool = applicationContext.getFmqMessagePool();
        }
        if (!pool.contains(key)) {
            throw new FmqDataKeyNotFoundException(String.format("message queue pool  not found dataKey {%s} ", key.getKey()));
        }
        return pool.getMessageQueue(key);
    }

    private void poll(FmqDataKey key, MessageQueue messageQueue) {
        FmqMessage<?> poll = messageQueue.poll();

        if (poll == null) {
            return;
        }

        RegisterManager registerManager = applicationContext.getRegisterManager();

        List<ClientListenerConnection> listenerConnection = registerManager.getListenerConnection(key);
        // TODO poll all message send to client
        log.info("consumer consume dataKey [{}]; message[{}] ", key.getKey(), poll.getBody());
    }

    @Override
    public void connect(FmqPool pool) {
        this.pool = pool;
    }
}
