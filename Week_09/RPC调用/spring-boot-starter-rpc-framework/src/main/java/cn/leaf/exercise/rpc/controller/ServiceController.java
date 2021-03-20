package cn.leaf.exercise.rpc.controller;

import cn.leaf.exercise.rpc.core.RpcRequest;
import cn.leaf.exercise.rpc.core.RpcResponse;
import cn.leaf.exercise.rpc.service.RpcServiceBeanFactory;
import cn.leaf.exercise.rpc.service.ServiceExecutor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.awt.*;

/**
 * @author 李克国
 * @date 2021/3/20
 */
@RestController
@RequestMapping("rpc/api")
@RequiredArgsConstructor
public class ServiceController {


    private final BeanFactory beanFactory;

    private final RpcServiceBeanFactory rpcServiceBeanFactory;


    private ServiceExecutor serviceExecutor;

    @PostConstruct
    public void initialize() {
        serviceExecutor = new ServiceExecutor(rpcServiceBeanFactory);
    }

    @RequestMapping("/{serviceName}")
    public RpcResponse invoke(@PathVariable("serviceName") String serviceName, @RequestBody RpcRequest request) {
        Object service = rpcServiceBeanFactory.getBean(serviceName);
        if (service == null) {
            service = beanFactory.getBean(serviceName);
            rpcServiceBeanFactory.addBean(serviceName, service);
        }
        if (service == null) {
            return RpcResponse.error(new ClassNotFoundException("service not found"));
        }

        return serviceExecutor.execute(request);
    }


}
