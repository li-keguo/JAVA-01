package cn.leaf.exercise.assembled.annotation;

import cn.leaf.exercise.assembled.common.AnnotationBeanProperties;
import cn.leaf.exercise.assembled.common.AssembledBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * spring bean装配 demo
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 11:47
 */
@Repository
@RequiredArgsConstructor
public class RepositoryBean implements AssembledBean {
    private final AnnotationBeanProperties properties;

    @Override
    public void show() {
        System.out.println("我是通过 @Repository 注解方式装配的 RepositoryBean " + properties);
    }
}
