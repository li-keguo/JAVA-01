package cn.leaf.exercise.rpc.core;

import lombok.Data;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@Data
public class RpcServiceDefinition<T> {

    private String url;

    private Class<T> clazz;
}
