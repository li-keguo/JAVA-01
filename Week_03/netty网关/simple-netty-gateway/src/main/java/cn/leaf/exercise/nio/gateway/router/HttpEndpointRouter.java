package cn.leaf.exercise.nio.gateway.router;

public interface HttpEndpointRouter {


    /**
     * 路由：给出服务名，返回指定的服务地址
     *
     * @param endpoints 服务名 例如：{baidu,b,baidu-search}
     * @return 服务地址 例如：www.baidu.com:80
     */
    String route(String endpoints);

    // Load Balance
    // Random
    // RoundRibbon 
    // Weight
    // - server01,20
    // - server02,30
    // - server03,50

}
