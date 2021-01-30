package cn.leaf.exercise.nio.gateway.router;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties实现
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/29 21:33
 * @description Properties 路由实现
 */
@Slf4j
public class PropertiesHttpEndpointRouterImpl implements HttpEndpointRouter {

    protected Properties propertiesRouterTable;

    private final String propertiesPath;
    /**
     * 约定默认的路由表配置路劲
     */
    public static final String DEFAULT_PROPERTIES_PATH = "MATE-INFO/";
    /**
     * 约定的路由表配置文件名
     */
    public static final String AGREEMENT_PROPERTIES_NAME = "router.properties";


    public PropertiesHttpEndpointRouterImpl(String propertiesPath) {
        this.propertiesPath = propertiesPath;
        initialize();
    }

    public PropertiesHttpEndpointRouterImpl() {
        this.propertiesPath = DEFAULT_PROPERTIES_PATH + AGREEMENT_PROPERTIES_NAME;
        initialize();
    }

    public void initialize() {
        if (propertiesPath == null) {
            throw new NullPointerException("Properties加载路由表初始化错误，缺少必要的配置文件信息");
        }
        this.propertiesRouterTable = new Properties();
        loadProperties();
    }

    @Override
    public String route(String servicesId) {
        return propertiesRouterTable.getProperty(servicesId, null);
    }

    private void loadProperties() {
        try {
            propertiesRouterTable.load(ResourceUtil.getStream(propertiesPath));
        } catch (IOException e) {
            log.error("Properties加载路由表加载失败", e);
        }
    }
}
