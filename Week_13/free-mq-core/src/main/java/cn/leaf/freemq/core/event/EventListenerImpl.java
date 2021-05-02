package cn.leaf.freemq.core.event;

import cn.leaf.freemq.core.consumer.FmqConsumer;
import cn.leaf.freemq.core.context.FmqApplicationContext;

/**
 * @author 李克国
 * @date 2021/5/2
 */
public class EventListenerImpl implements EventListener {
    @Override
    public void accept(FmqEvent event) {
        FmqApplicationContext applicationContext = event.getApplicationContext();
        FmqConsumer consumer = applicationContext.getCurrentConsumer();
        consumer.subscribe(event.getDataKey());
    }
}
