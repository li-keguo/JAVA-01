package cn.leaf.exercise.multi.datasource;

import cn.leaf.exercise.multi.datasource.config.SlaveDataSourceProperties;
import cn.leaf.exercise.multi.datasource.demo.SwitchDataSourceDemo;
import cn.leaf.exercise.multi.datasource.repository.mapper.XmShoppingOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 李克国
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class MultiDataSourceApplication implements ApplicationRunner {

    private final SlaveDataSourceProperties properties;

    private final SwitchDataSourceDemo demo;

    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("master data-source: {}  {}", properties.getMaster().getName(), properties.getMaster().getName());
        properties.getSlaves().forEach((k, v) -> log.debug("slave data-source: {}  {}", k, v.getName()));
        // 使用 @Switch 选择执行数据库
        demo.annotationSwitchSlave1();
//        // 使用代码切换数据源执行
//        demo.codeSwitchSlave1();
//        demo.flush();

    }
}
