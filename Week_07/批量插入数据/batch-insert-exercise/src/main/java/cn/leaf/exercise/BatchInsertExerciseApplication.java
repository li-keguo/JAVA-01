package cn.leaf.exercise;

import cn.leaf.exercise.demo.JdbcBatchInsertDemo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 李克国
 * @date 2021-03-03
 */
@RequiredArgsConstructor
@SpringBootApplication
@MapperScan("cn.leaf.exercise.repository.mapper")
public class BatchInsertExerciseApplication implements ApplicationRunner {

    private final JdbcBatchInsertDemo jdbcTemplateDemo;

    public static void main(String[] args) {
        SpringApplication.run(BatchInsertExerciseApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jdbcTemplateDemo.mybatisParallelInsertDemo();
    }
}
