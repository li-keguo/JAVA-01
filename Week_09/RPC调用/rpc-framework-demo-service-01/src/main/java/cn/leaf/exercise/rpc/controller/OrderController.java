package cn.leaf.exercise.rpc.controller;

import cn.leaf.exercise.model.po.XmShoppingOrder;
import cn.leaf.exercise.rpc.api.OrderService;
import cn.leaf.exercise.rpc.model.entity.ServiceResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RequestMapping("/{orderId}")
    public ResponseEntity<ServiceResultEntity<XmShoppingOrder>> get(@PathVariable("orderId") String orderId){
        CollectionUtils.is
        return ResponseEntity.ok(orderService.get(orderId));
    }
}
