package cn.leaf.exercise.multi.datasource.support.datasource;

import cn.hutool.core.util.ClassUtil;
import cn.leaf.exercise.multi.datasource.config.SlaveDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多数据源路由
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/3 14:12
 * @description 多数据源路由
 */
@Component
@RequiredArgsConstructor
public class MultiDataSourceRouter extends AbstractRoutingDataSource {

    private final SlaveDataSourceProperties slavesProperties;

    private Map<String, DataSource> dataSourceMapping;
    private ThreadLocal<String> currentDataSource;

    @PostConstruct
    public void initiator() {
        currentDataSource = new ThreadLocal<>();
        dataSourceMapping = new ConcurrentHashMap<>(16);
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>(slavesProperties.getSlaves().size() + 1);
        HikariDataSource masterDataSource = buildDataSource("master", slavesProperties.getMaster());
        concurrentHashMap.put("master", masterDataSource);
        dataSourceMapping.put("master", masterDataSource);
        currentDataSource.set("master");
        setDefaultTargetDataSource(masterDataSource);
        slavesProperties.getSlaves().forEach((k, v) -> {
            // 添加从库
            HikariDataSource slave = buildDataSource(k, v);
            concurrentHashMap.put(k, slave);
            dataSourceMapping.put(k, slave);
        });
        setTargetDataSources(concurrentHashMap);
    }

    private HikariDataSource buildDataSource(String name, DataSourceProperties properties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName(name);
        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUsername());
        hikariConfig.setPassword(properties.getPassword());
        return new HikariDataSource(hikariConfig);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public String getDataSource() {
        return currentDataSource.get();
    }

    public void switchDataSource(String dataSourceName) {
        currentDataSource.set(dataSourceName);
    }
}
