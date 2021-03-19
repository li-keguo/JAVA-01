package cn.leaf.exercise.rpc.api;

import cn.leaf.exercise.model.po.XmConsumer;
import cn.leaf.exercise.rpc.model.entity.ServiceResultEntity;

/**
 * @author 李克国
 * @date 2021/3/19
 */
public interface CustomerService {


    /**
     * 获取一个消费者
     * @param consumerId consumerId
     * @return consumer
     */
    ServiceResultEntity<XmConsumer> get(String consumerId);
}
