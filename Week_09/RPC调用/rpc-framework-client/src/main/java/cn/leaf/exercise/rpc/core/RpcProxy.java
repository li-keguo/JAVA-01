package cn.leaf.exercise.rpc.core;

import cn.hutool.core.convert.Convert;
import cn.leaf.exercise.rpc.core.filter.RpcFilter;
import cn.leaf.exercise.rpc.core.invoker.RpcInvoker;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@Slf4j
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
                .method(method.getName())
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
            RpcResponse response = invoker.invoke(request);
            if (response.getThrowable() != null) {
                log.error("rpc exception: remote method [{}] execute exception", method.getName());
                throw response.getThrowable();
            }
            if (method.getReturnType().getName().equals("void")) {
                return null;
            }
            return Convert.convert(method.getReturnType(), response.getBody());
        }
        return null;
    }

    public void addFilter(RpcFilter filter) {
        if (filters == null) {
            filters = new CopyOnWriteArrayList<>();
        }
        filters.add(filter);
    }
}
