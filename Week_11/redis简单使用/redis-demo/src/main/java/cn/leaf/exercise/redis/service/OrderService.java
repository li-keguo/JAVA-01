package cn.leaf.exercise.redis.service;

import java.util.List;

/**
 * @author 李克国
 * @date 2021/4/5
 */
public interface OrderService {

    /**
     * 下单
     * @param userId 用户id
     * @param productIds 产品id
     */
    void order(String userId, List<String> productIds);
}
