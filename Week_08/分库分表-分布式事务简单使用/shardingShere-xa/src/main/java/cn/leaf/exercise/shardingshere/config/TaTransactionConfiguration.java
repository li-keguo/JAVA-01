package cn.leaf.exercise.shardingshere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/3/12 22:16
 */
@Configuration
@EnableTransactionManagement
public class TaTransactionConfiguration {



    /**
     * Create platform transaction manager bean.
     *
     * @param dataSource data source
     * @return platform transaction manager
     */
    @Bean
    public PlatformTransactionManager txManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
