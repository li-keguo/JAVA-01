package cn.leaf.exercise.multi.datasource.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 多相关从库配置
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/3 14:22
 * @description 多相关从库配置
 */
@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "multi-data-source")
public class SlaveDataSourceProperties {

    private final DataSourceProperties master;

    private Map<String, DataSourceProperties> slaves;
}
