package cn.leaf.exercise.redis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 模拟每个服务争抢的资源
 * @author 李克国
 * @date 2021/4/5
 */
@Data
@Builder
@AllArgsConstructor
public class MockResource {

    /**
     * 库存
     */
    private Integer stock;
}
