package cn.leaf.exercise.rpc.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李克国
 * @date 2021/3/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResponse {

    private Object body;

    private Throwable throwable;

    RpcResponse(Object body) {
        this.body = body;
    }

    public static RpcResponse empty() {
        return new RpcResponse();
    }

    public static RpcResponse error(Throwable error) {
        RpcResponse response = new RpcResponse();
        response.setThrowable(error);
        return response;
    }
}
