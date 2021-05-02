package cn.leaf.freemq.core.consumer;

import cn.leaf.freemq.common.exception.FmqDataKeyNotFoundException;
import cn.leaf.freemq.common.exception.FmqPoolNotFoundExeption;
import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.core.pool.FmqPool;
import cn.leaf.freemq.core.pool.MessageQueue;
import cn.leaf.freemq.core.register.ClientListenerConnection;
import cn.leaf.freemq.core.register.RegisterManager;
import cn.leaf.freemq.model.FmqDataKey;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void subscribe(FmqDataKey key) {
        if (pool == null) {
            throw new FmqPoolNotFoundExeption("message queue pool is not connection ");
        }
        if (!pool.contains(key)) {
            throw new FmqDataKeyNotFoundException(String.format("message queue pool  not found dataKey {%s} ", key.getKey()));
        }
        MessageQueue messageQueue = pool.getMessageQueue(key);
        // TODO poll all message send to client
        messageQueue.poll();

        RegisterManager registerManager = applicationContext.getRegisterManager();

        List<ClientListenerConnection> listenerConnection = registerManager.getListenerConnection(key);

        log.info("consumer consumer dataKey [{}]  ",key.getKey());
    }

    @Override
    public void connect(FmqPool pool) {
        this.pool = pool;
    }
}
