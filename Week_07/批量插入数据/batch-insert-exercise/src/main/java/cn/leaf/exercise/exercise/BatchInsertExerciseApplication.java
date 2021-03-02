package cn.leaf.exercise.exercise;

import cn.leaf.exercise.exercise.jdbc.JdbcBatchInsertDemo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@RequiredArgsConstructor
@SpringBootApplication
public class BatchInsertExerciseApplication implements ApplicationRunner {

    private final JdbcBatchInsertDemo jdbcTemplateDemo;

    public static void main(String[] args) {
        SpringApplication.run(BatchInsertExerciseApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jdbcTemplateDemo.parallelPrimitiveJdbcInsertDemo();
    }
}
