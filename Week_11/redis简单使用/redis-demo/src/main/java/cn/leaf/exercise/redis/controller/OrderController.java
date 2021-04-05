package cn.leaf.exercise.redis.controller;

import cn.hutool.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 李克国
 * @date 2021/4/5
 */

@RestController
@RequestMapping("/order")
public class OrderController {


    @Resource(name = "publish")
    private Jedis jedis;

    @RequestMapping("/sync/{userId}")
    public ResponseEntity<String> order(@PathVariable("userId") String userId, @RequestBody List<String> productIds) {
        JSONArray objects = new JSONArray();
        objects.set(0, userId);
        objects.set(1, productIds);
        // 发布订单消息：异步处理
        jedis.publish("order", objects.toString());
        return ResponseEntity.ok("processing ...");
    }
}
