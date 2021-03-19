package cn.leaf.exercise.rpc.api;

import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.rpc.model.entity.ServiceResultEntity;

/**
 * @author 李克国
 */
public interface OrderService {


    /**
     * get one order
     * @param orderId orderId
     * @return order
     */
    ServiceResultEntity<XmShoppingOrder> get(String orderId);


}
