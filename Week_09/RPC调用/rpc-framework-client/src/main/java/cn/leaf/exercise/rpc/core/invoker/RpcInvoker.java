package cn.leaf.exercise.rpc.core.invoker;

import cn.leaf.exercise.rpc.core.RpcRequest;
import cn.leaf.exercise.rpc.core.RpcResponse;

/**
 * @author 李克国
 * @date 2021/3/19
 */
public interface RpcInvoker {

    /**
     * rpc invoker
     * @param request request
     * @return response
     */
    RpcResponse invoke(RpcRequest request);
}
