package cn.leaf.exercise.rpc.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Proxy;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@RequiredArgsConstructor
public class RpcProxyFactory<T> {
    @Getter
    private final java.lang.Class<T> interfaceClass;

    public T newInstance(RpcProxy<T> rpcProxy) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, rpcProxy);
    }

}
