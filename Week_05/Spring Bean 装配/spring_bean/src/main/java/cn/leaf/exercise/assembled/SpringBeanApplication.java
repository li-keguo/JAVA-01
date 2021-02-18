package cn.leaf.exercise.assembled;

import cn.leaf.exercise.assembled.common.AssembledBean;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

/**
 * @author 李克国
 */
@RequiredArgsConstructor
@SpringBootApplication
public class SpringBeanApplication implements ApplicationRunner {

    private final Map<String, AssembledBean> assembledBeans;

    public static void main(String[] args) {
        SpringApplication.run(SpringBeanApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        assembledBeans.forEach((k, v) -> v.show());
    }

}
