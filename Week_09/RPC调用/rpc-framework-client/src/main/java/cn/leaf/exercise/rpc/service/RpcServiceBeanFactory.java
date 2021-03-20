package cn.leaf.exercise.rpc.service;

/**
 * @author 李克国
 * @date 2021/3/20
 */
public interface RpcServiceBeanFactory {

    /**
     * 获取service实现
     * @param name service name
     * @return service impl
     */
    Object getBean(String name);


    /**
     * add bean
     * @param name name
     * @param bean bean
     */
    void addBean(String name,Object bean);
}
