package cn.leaf.exercise.rpc.core.filter;

import cn.leaf.exercise.rpc.core.RpcRequest;

/**
 * @author 李克国
 * @date 2021/3/19
 */
public interface RpcFilter {

    /**
     * filter
     * @param request request
     * @return is pass
     */
    boolean filter(RpcRequest request);
}
