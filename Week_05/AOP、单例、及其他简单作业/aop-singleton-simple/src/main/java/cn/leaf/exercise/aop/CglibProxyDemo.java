package cn.leaf.exercise.aop;

import cn.leaf.exercise.common.AopServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * cglib 动态代理 demo
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/13 17:54
 */
public class CglibProxyDemo {
    public static void main(String[] args) {
        AopServiceMethodInterceptor interceptor = new AopServiceMethodInterceptor();
        interceptor.addProxyMethod("execute");
        interceptor.addProxyMethod("start");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AopServiceImpl.class);
        enhancer.setCallback(interceptor);

        AopServiceImpl proxyAopService = (AopServiceImpl) enhancer.create();
        proxyAopService.execute();
        proxyAopService.start();
        proxyAopService.run();
    }

    static class AopServiceMethodInterceptor implements MethodInterceptor {
        private final List<String> proxyMethodName;

        public AopServiceMethodInterceptor() {
            proxyMethodName = new ArrayList<String>(4);
        }

        public void addProxyMethod(String methodName) {
            proxyMethodName.add(methodName);
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Object invokeReturn;
            if (proxyMethodName.contains(method.getName())) {
                System.out.println(String.format("[Thread:--- %s] proxy method(%s) before", Thread.currentThread().getName(), method.getName()));
                invokeReturn = methodProxy.invokeSuper(o, objects);
                System.out.println(String.format("[Thread:--- %s] proxy method(%s) after", Thread.currentThread().getName(), method.getName()));
                return invokeReturn;
            }
            invokeReturn = methodProxy.invokeSuper(o, objects);
            return invokeReturn;
        }
    }
}
