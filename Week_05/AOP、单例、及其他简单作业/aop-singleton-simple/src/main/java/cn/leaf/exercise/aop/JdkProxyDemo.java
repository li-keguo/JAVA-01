package cn.leaf.exercise.aop;

import cn.leaf.exercise.common.AopService;
import cn.leaf.exercise.common.AopServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * jdk 动态代理demo
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/13 17:27
 */
public class JdkProxyDemo {

    public static void main(String[] args) {
        AopServiceInvocationHandler invocationHandler = new AopServiceInvocationHandler(new AopServiceImpl());
        invocationHandler.addProxyMethod("execute");
        invocationHandler.addProxyMethod("start");
        AopService proxyAopService = (AopService) Proxy.newProxyInstance(AopServiceInvocationHandler.class.getClassLoader(), AopServiceImpl.class.getInterfaces(), invocationHandler);
        proxyAopService.execute();
        proxyAopService.run();
        proxyAopService.start();
    }

    static class AopServiceInvocationHandler implements InvocationHandler {

        private final AopService aopService;

        private final List<String> proxyMethodName;

        public AopServiceInvocationHandler(AopService aopService) {
            this.aopService = aopService;
            proxyMethodName = new ArrayList<String>(4);
        }

        public void addProxyMethod(String methodName) {
            proxyMethodName.add(methodName);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object invokeReturn;
            if (proxyMethodName.contains(method.getName())) {
                System.out.println(String.format("[Thread:--- %s] proxy method(%s) before", Thread.currentThread().getName(), method.getName()));
                invokeReturn = method.invoke(aopService, args);
                System.out.println(String.format("[Thread:--- %s] proxy method(%s) after", Thread.currentThread().getName(), method.getName()));
                return invokeReturn;
            }
            invokeReturn = method.invoke(aopService, args);
            return invokeReturn;
        }
    }
}
