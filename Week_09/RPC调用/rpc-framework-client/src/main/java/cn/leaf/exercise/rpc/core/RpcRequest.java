package cn.leaf.exercise.rpc.core;

import lombok.*;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest {

    private String url;

    private String serviceName;

    private String method;

    private Class<?> ServiceInterface;

    private Object[] args;
}
