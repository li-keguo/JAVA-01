package cn.leaf.freemq.core.event;

public interface EventListener{
    /**
     * 收到消息之后的回调
     * @param event event
     */
    void accept(FmqEvent event);
}
