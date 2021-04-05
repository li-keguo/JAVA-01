package cn.leaf.exercise.redis.pubsub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

/**
 * @author 李克国
 * @date 2021/4/5
 */
@Slf4j
public class PubSubListenerService extends JedisPubSub {

    /**
     * 监听到订阅频道接收到消息
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("onSubscribe: channel[%s], " + "message[%s]", channel, message));
    }

    /**
     * 监听到订阅模式接收到消息
     */
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        log.info(String.format("onPMessage: pattern[%s], channel[%s], message[%s]", pattern, channel, message));
    }

    /**
     * 订阅频道时的回调
     *
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        log.info(String.format("onSubscribe: channel[%s], " + "subscribedChannels[%s]", channel, subscribedChannels));
    }

    /**
     * 取消订阅频道时的回调
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        log.info(String.format("onUnsubscribe: channel[%s], " + "subscribedChannels[%s]", channel, subscribedChannels));
    }

    /**
     * 取消订阅模式时的回调
     */
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        log.info(String.format("onPUnsubscribe: pattern[%s], " + "subscribedChannels[%s]", pattern, subscribedChannels));
    }

    /**
     * 订阅频道模式时的回调
     */
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info(String.format("onPSubscribe: pattern[%s], " + "subscribedChannels[%s]", pattern, subscribedChannels));
    }
}
