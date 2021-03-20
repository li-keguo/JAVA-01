package cn.leaf.exercise.rpc.config;

import cn.hutool.core.util.ClassUtil;
import cn.leaf.exercise.rpc.clent.RpcProxyFactory;
import cn.leaf.exercise.rpc.core.annotation.RpcClient;
import cn.leaf.exercise.rpc.process.RpcBeanDefinitionRegistryPostProcessor;
import cn.leaf.exercise.rpc.service.RpcServiceBeanFactory;
import cn.leaf.exercise.rpc.service.RpcServiceCacheBeanFactoryImpl;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author 李克国
 * @date 2021/3/20
 */
@Configuration
public class RpcConfiguration {


    public  void test(){
        Set<Class<?>> services = ClassUtil.scanPackageByAnnotation("", RpcClient.class);
    }

//    @Bean
//    public BeanDefinitionRegistryPostProcessor BeanDefinitionRegistryPostProcessor(){
//        return new RpcBeanDefinitionRegistryPostProcessor();
//    }

    @Bean
    public RpcServiceBeanFactory rpcServiceBeanFactory(){
        return new RpcServiceCacheBeanFactoryImpl();
    }
}
