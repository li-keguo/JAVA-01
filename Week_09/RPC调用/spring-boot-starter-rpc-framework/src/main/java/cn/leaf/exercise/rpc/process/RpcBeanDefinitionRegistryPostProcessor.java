package cn.leaf.exercise.rpc.process;

import cn.hutool.core.util.ClassUtil;
import cn.leaf.exercise.rpc.clent.RpcProxyFactory;
import cn.leaf.exercise.rpc.core.RpcProxy;
import cn.leaf.exercise.rpc.core.RpcResponse;
import cn.leaf.exercise.rpc.core.RpcServiceDefinition;
import cn.leaf.exercise.rpc.core.annotation.RpcClient;
import cn.leaf.exercise.rpc.core.invoker.MockRpcInvokerImpl;
import cn.leaf.exercise.rpc.core.invoker.RpcInvoker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RpcBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    // 先写死 ，后去注解或者配置获取扫描路径信息
    private String basePackageName = "cn.leaf.exercise.service";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<Class<?>> services = ClassUtil.scanPackageByAnnotation(basePackageName, RpcClient.class);
        for (Class<?> service : services) {
            RpcProxyFactory<?> rpcProxyFactory = new RpcProxyFactory<>(service);
            RpcServiceDefinition<?> definition = new RpcServiceDefinition<>();
//            RpcInvoker invoker = new HttpClientRpcInvokerImpl();
            RpcInvoker invoker = new MockRpcInvokerImpl(RpcResponse.empty());
            RpcProxy rpcProxy = new RpcProxy<>(definition, invoker);
            Object o = rpcProxyFactory.newInstance(rpcProxy);
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(o.getClass()).getBeanDefinition();
            registry.registerBeanDefinition(service.getName(), beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
