package cn.leaf.exercise.nio.gateway.router;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由配置
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @Date 2021/1/30 15:41
 * @description 路由配置
 */
public class RouterConfig {

    private static final List<HttpEndpointRouter> routes = new ArrayList<>();

    public static String route(String serviceName) {
        for (HttpEndpointRouter route : routes) {
            String target = route.route(serviceName);
            if (target != null) {
                return target;
            }
        }
        return null;
    }

    public static void registerRouter(HttpEndpointRouter router) {
        routes.add(router);
    }
}
