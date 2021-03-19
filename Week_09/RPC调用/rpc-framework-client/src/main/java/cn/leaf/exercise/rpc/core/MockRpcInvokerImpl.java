package cn.leaf.exercise.rpc.core;

import lombok.RequiredArgsConstructor;

/**
 * mock impl
 *
 * @author 李克国
 * @date 2021/3/19
 */
@RequiredArgsConstructor
public class MockRpcInvokerImpl implements RpcInvoker {

    private final RpcResponse mockResponse;

    @Override
    public RpcResponse invoke(RpcRequest request) {
        return mockResponse;
    }
}
