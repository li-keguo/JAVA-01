package cn.leaf.freemq.core.event;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project free-mq
 * @date 2021/4/28 15:45
 */
public interface EventManager {


    /**
     * publish
     *
     * @param event event
     */
    void publish(FmqEvent event);

    /**
     * 注册事件监听器
     * @param listener listener
     */
    void registerEventListener(EventListener listener);
}
