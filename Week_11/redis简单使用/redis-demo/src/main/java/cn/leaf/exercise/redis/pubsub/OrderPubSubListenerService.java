package cn.leaf.exercise.redis.pubsub;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.leaf.exercise.redis.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李克国
 * @date 2021/4/5
 */
@Service
@RequiredArgsConstructor
public class OrderPubSubListenerService extends PubSubListenerService {

    private final OrderService orderService;

    private final String ORDER_CHANNEL = "order";

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        if (ORDER_CHANNEL.equals(channel)) {
            JSONArray objects = JSONUtil.parseArray(message);
            orderService.order(objects.getStr(0,null), objects.get(1, List.class));
        }

    }
}
