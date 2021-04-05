package cn.leaf.exercise.redis.service.impl;

import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.redis.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author 李克国
 * @date 2021/4/5
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void order(String userId, List<String> productIds) {
        // 下单
        XmShoppingOrder.builder()
                .orderId(UUID.randomUUID().toString())
                .build();
    }
}
