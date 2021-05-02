package cn.leaf.freemq.core.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 事件管理器
 * @author 刘天佑
 * @date 2021-4-29 16:20:18
 */
public class DefaultEventManager implements EventManager {
    private final List<EventListener> listeners  = new CopyOnWriteArrayList<>();
    @Override
    public void publish(FmqEvent event) {
        // TODO 此处修改为 异步加线程池的方式，publish 只做唤醒线程执行
        for (EventListener listener : listeners) {
            listener.accept(event);
        }
    }

    @Override
    public void registerEventListener(EventListener listener) {
        this.listeners.add(listener);
    }
}
