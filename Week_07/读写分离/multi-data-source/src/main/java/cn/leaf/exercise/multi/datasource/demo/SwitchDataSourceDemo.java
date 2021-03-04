package cn.leaf.exercise.multi.datasource.demo;

import cn.leaf.exercise.multi.datasource.repository.mapper.XmShoppingOrderMapper;
import cn.leaf.exercise.multi.datasource.model.po.XmShoppingOrder;
import cn.leaf.exercise.multi.datasource.support.annotation.Switch;
import cn.leaf.exercise.multi.datasource.support.actuator.SwitchDataSourceActuator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 选择数据源 执行 demo
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/4 14:52
 * @description 选择数据源 执行 demo
 */
@Component
@RequiredArgsConstructor
public class SwitchDataSourceDemo {

    private final XmShoppingOrderMapper orderMapper;

    private final SwitchDataSourceActuator dataSourceActuator;

    /**
     * annotation 方式实现切换数据库执行
     */
    @Switch(dataSources = "slave-1")
    public void annotationSwitchSlave1() {
        XmShoppingOrder condition = new XmShoppingOrder();
        condition.setOrderId("1");
        List<XmShoppingOrder> select = orderMapper.select(condition);
        select.forEach(System.out::println);
    }

    /**
     * 代码的式实现切换数据库执行
     */
    public void codeSwitchSlave1() {
        XmShoppingOrder condition = new XmShoppingOrder();
        condition.setOrderId("1");
        List<XmShoppingOrder> select = dataSourceActuator.actuate("slave-1", () -> orderMapper.select(condition));
        select.forEach(System.out::println);
    }
}
