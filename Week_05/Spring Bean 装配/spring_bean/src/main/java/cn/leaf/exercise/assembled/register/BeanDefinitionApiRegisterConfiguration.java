package cn.leaf.exercise.assembled.register;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * 通过{@link org.springframework.beans.factory.config.BeanDefinition} api 注册到 ioc 容器的方式装配 bean 的配置
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 20:34
 */
@Configuration
@RequiredArgsConstructor
@Order(1)
public class BeanDefinitionApiRegisterConfiguration {

    private final AnnotationConfigApplicationContext beanDefinitionRegistry;

    @PostConstruct
    public void configure() {
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(BeanDefinitionApiRegisterBean.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("apiRegister", beanDefinition);
    }
}
