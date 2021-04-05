package cn.leaf.exercise.redis;

import lombok.extern.slf4j.Slf4j;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 客户端订阅监听类
 * 
 * @author
 *
 */
@Slf4j
public class PubSubService extends JedisPubSub {

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
    
    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379, 0);// redis服务地址和端口号

            PubSubService pubSub = new PubSubService();
            Jedis finalJedis = jedis;
            new Thread(()->{
                finalJedis.subscribe(pubSub, "news.share", "news.blog","order");
            }).start();

            jedis = new Jedis("127.0.0.1", 6379, 0);// redis服务地址和端口号

            System.out.println("here ... ");
            for (int i = 0; i < 100; i++) {

                Thread.sleep(1000);
                jedis.publish("order","order");
            }
            System.out.println("here .. end ");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.disconnect();
            }
        }
    }
}