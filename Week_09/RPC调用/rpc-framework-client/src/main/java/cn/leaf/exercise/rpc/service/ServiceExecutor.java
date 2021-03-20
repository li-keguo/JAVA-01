package cn.leaf.exercise.rpc.service;

import cn.leaf.exercise.rpc.core.RpcRequest;
import cn.leaf.exercise.rpc.core.RpcResponse;
import cn.leaf.exercise.rpc.core.invoker.ServiceRpcInvokerImpl;
import lombok.RequiredArgsConstructor;

/**
 * service provider common api
 *
 * @author 李克国
 * @date 2021/3/20
 */
@RequiredArgsConstructor
public class ServiceExecutor {

    private final RpcServiceBeanFactory beanFactory;


    public RpcResponse execute(RpcRequest request) {
        Object service = beanFactory.getBean(request.getServiceName());
        return new ServiceRpcInvokerImpl(service).invoke(request);
    }
}
