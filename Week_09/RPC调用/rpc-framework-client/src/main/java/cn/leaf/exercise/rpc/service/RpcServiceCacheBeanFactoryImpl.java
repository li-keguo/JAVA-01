package cn.leaf.exercise.rpc.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 李克国
 * @date 2021/3/20
 */
public class RpcServiceCacheBeanFactoryImpl implements RpcServiceBeanFactory {

    private final Map<String, Object> localCache = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return localCache.get(name);
    }

    public void cache(String name, Object service) {
        localCache.put(name, service);
    }

    @Override
    public void addBean(String name, Object bean) {
        cache(name, bean);
    }
}
