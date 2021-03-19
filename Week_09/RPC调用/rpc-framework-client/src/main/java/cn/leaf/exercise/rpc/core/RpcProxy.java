package cn.leaf.exercise.rpc.core;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@RequiredArgsConstructor
public class RpcProxy<T> implements InvocationHandler, Serializable {

    private final RpcServiceDefinition<T> serviceDefinition;

    private final RpcInvoker invoker;

    @Setter
    private Collection<RpcFilter> filters;

//    private final

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(args);
        }
        RpcRequest request = RpcRequest.builder()
                .url(serviceDefinition.getUrl())
                .args(args)
                .build();
        boolean isPass = true;
        if (filters != null && filters.size() > 0) {
            for (RpcFilter filter : filters) {
                isPass = isPass && filter.filter(request);
            }
        }
        if (isPass) {
            //  appoint to invoker
            return invoker.invoke(request);
        }
        return RpcResponse.empty();
    }

    public void addFilter(RpcFilter filter) {
        if (filters == null) {
            filters = new CopyOnWriteArrayList<>();
        }
        filters.add(filter);
    }
}
