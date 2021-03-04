package cn.leaf.exercise.multi.datasource.support.annotation;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/3 16:56
 * @description TODO
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Switch {

    /**
     * 数据源  默认为 master
     *
     * @return Sting
     */
    String dataSources() default "master";

}
