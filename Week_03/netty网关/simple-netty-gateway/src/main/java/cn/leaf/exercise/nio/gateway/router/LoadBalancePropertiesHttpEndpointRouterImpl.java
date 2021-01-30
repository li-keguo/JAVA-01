package cn.leaf.exercise.nio.gateway.router;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link PropertiesHttpEndpointRouterImpl}升级版，提供负载均衡能力,本类负载实现为顺序轮询
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 16:22
 * @description {@link PropertiesHttpEndpointRouterImpl} 升级版，提供负载均衡能力
 */
public class LoadBalancePropertiesHttpEndpointRouterImpl extends PropertiesHttpEndpointRouterImpl implements HttpEndpointRouter {

    private final Map<String, RouterArray> routers;

    public final String SPLITE_CODE = ",";

    public LoadBalancePropertiesHttpEndpointRouterImpl(String propertiesPath) {
        super(propertiesPath);
        routers = new HashMap<>(propertiesRouterTable.size());
        init();
    }

    public LoadBalancePropertiesHttpEndpointRouterImpl() {
        super();
        routers = new HashMap<>(propertiesRouterTable.size());
        init();
    }

    private void init() {
        propertiesRouterTable.forEach((k, v) -> {
            if (v == null) {
                throw new NullPointerException("服务" + k + "找到对应的真实地址");
            }
            routers.put(k.toString(), RouterArray.builder()
                    .routers(v.toString().split(SPLITE_CODE))
                    .build());
        });
    }

    @Override
    public String route(String endpoints) {
        RouterArray routerArray = routers.get(endpoints);
        if (routerArray == null) {
            return super.route(endpoints);
        }
        return routerArray.next();
    }

    @Data
    @Builder
    @AllArgsConstructor
    static class RouterArray {
        String[] routers;
        @Builder.Default
        int loopIndex = 0;

        public String next() {
            loopIndex++;
            if (loopIndex >= routers.length) {
                loopIndex = 0;
            }
            return routers[loopIndex];
        }
    }
}
