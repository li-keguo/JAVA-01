package cn.leaf.exercise.rpc.core;

import cn.leaf.exercise.rpc.clent.RpcProxyFactory;
import cn.leaf.exercise.rpc.core.invoker.MockRpcInvokerImpl;
import org.junit.jupiter.api.Test;

class RpcProxyFactoryTest {
    

    @Test
    public void test(){
        RpcProxyFactory<TestService> proxyFactory = new RpcProxyFactory<>(TestService.class);

        MockRpcInvokerImpl mockRpcInvoker = new MockRpcInvokerImpl(new RpcResponse("test"));
        RpcServiceDefinition<TestService> serviceDefinition = new RpcServiceDefinition<>();
        serviceDefinition.setClazz(TestService.class);

        RpcProxy<TestService> rpcProxy = new RpcProxy<>(serviceDefinition, mockRpcInvoker);
        TestService testService = proxyFactory.newInstance(rpcProxy);
        String test = testService.test();
        System.out.println(test);
        testService.testVoid();
    }

    interface TestService{
        String test();

        void testVoid();
    }
}