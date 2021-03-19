package cn.leaf.exercise.rpc.service;

import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.repository.mapper.XmShoppingOrderMapper;
import cn.leaf.exercise.rpc.api.OrderService;
import cn.leaf.exercise.rpc.model.entity.ServiceResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final XmShoppingOrderMapper orderMapper;

    @Override
    public ServiceResultEntity<XmShoppingOrder> get(String orderId) {
        XmShoppingOrder order = orderMapper.selectByPrimaryKey(orderId);
        return ServiceResultEntity.result(order);
    }
}
