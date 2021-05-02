package cn.leaf.freemq.core.event;

import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.model.FmqDataKey;
import lombok.RequiredArgsConstructor;

/**
 * message publish event
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 15:43
 */
@RequiredArgsConstructor
public class MessagePublishFmqEvent implements FmqEvent {

    private final FmqApplicationContext applicationContext;

    private final FmqDataKey dataKey;

    @Override
    public FmqApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public FmqDataKey getDataKey() {
        return dataKey;
    }
}
