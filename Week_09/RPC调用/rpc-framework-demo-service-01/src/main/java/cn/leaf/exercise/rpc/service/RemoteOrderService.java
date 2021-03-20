package cn.leaf.exercise.rpc.service;

import cn.leaf.exercise.rpc.api.OrderService;
import cn.leaf.exercise.rpc.core.annotation.RpcClient;


/**
 * @author 李克国
 * @date 2021/3/19
 */
@RpcClient(name = "remoteOrderService")
public interface RemoteOrderService extends OrderService {

}
