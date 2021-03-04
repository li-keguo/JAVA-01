package cn.leaf.exercise.multi.datasource.support.actuator;

import cn.leaf.exercise.multi.datasource.support.datasource.MultiDataSourceRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 选择数据源执行器执行器
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/4 15:01
 * @description 选择数据源执行器执行器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SwitchDataSourceActuator {
    private final MultiDataSourceRouter dataSourceRouter;

    /**
     * 选择数据源执行
     *
     * @param targetDataSource 目标 数据源
     * @param supplier         执行函数
     * @param <T>              返回类型
     * @return <T>
     */
    public <T> T actuate(String targetDataSource, Supplier<T> supplier) {
        String dataSource = dataSourceRouter.getDataSource();
        log.debug("原始数据源 {}，本次执行使用数据源 {};执行开始", dataSource, targetDataSource);
        dataSourceRouter.switchDataSource(targetDataSource);
        T t = supplier.get();
        log.debug("原始数据源 {}，本次执行使用数据源 {};执行完毕，将切回原数据源 {}", dataSource, targetDataSource, dataSource);
        dataSourceRouter.switchDataSource(dataSource);
        return t;
    }
}
