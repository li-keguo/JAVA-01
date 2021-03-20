package cn.leaf.exercise.rpc.core.invoker;

import cn.hutool.core.util.ClassUtil;
import cn.leaf.exercise.rpc.core.RpcRequest;
import cn.leaf.exercise.rpc.core.RpcResponse;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 李克国
 * @date 2021/3/20
 */
@RequiredArgsConstructor
public class ServiceRpcInvokerImpl implements RpcInvoker {

    private final Object serviceTarget;

    @Override
    public RpcResponse invoke(RpcRequest request) {
        Method method = ClassUtil.getDeclaredMethod(serviceTarget.getClass(), request.getMethod());
        RpcResponse response = new RpcResponse();
        try {
            Object resultBody = method.invoke(serviceTarget, request.getArgs());
            response.setBody(resultBody);
        } catch (Exception e) {
            response.setThrowable(e);
        }
        return response;
    }
}
