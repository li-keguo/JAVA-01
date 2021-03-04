package cn.leaf.exercise.multi.datasource.support.interrupter;

import cn.leaf.exercise.multi.datasource.support.annotation.Switch;
import cn.leaf.exercise.multi.datasource.support.datasource.MultiDataSourceRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 64 bytes from 52.74.223.119: icmp_seq=9 ttl=44 time=81.891 ms
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/3 17:00
 * @description TODO
 */

@Aspect
@Component
@Order(10)
@Slf4j
@RequiredArgsConstructor
public class SwitchAroundInterrupter {

    private final MultiDataSourceRouter dataSourceRouter;

    @Pointcut("@annotation(cn.leaf.exercise.multi.datasource.support.annotation.Switch)")
    public void userAccess() {
    }

    @Around(value = "@within(switchs) || " + "@annotation(switchs)")
    public Object verifyRoleExecuteCommand(ProceedingJoinPoint pjp, Switch switchs) throws Throwable {
        String dataSource = dataSourceRouter.getDataSource();
        log.debug("原始数据源 {}，本次执行使用数据源 {};执行开始", dataSource, switchs.dataSources());
        dataSourceRouter.switchDataSource(switchs.dataSources());
        Object result = pjp.proceed();
        log.debug("原始数据源 {}，本次执行使用数据源 {};执行完毕，将切回原数据源 {}", dataSource, switchs.dataSources(), dataSource);
        dataSourceRouter.switchDataSource(dataSource);
        return result;
    }
}
