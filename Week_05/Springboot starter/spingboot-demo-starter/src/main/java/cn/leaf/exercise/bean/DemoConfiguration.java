package cn.leaf.exercise.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李克国
 */
@Configuration
public class DemoConfiguration {


    @Bean(name = "student100")
    public Student student() {
        return Student.create();
    }

    @Bean
    public Klass klass() {
        return new Klass();
    }

    @Bean
    public School school(Klass class1, Student student100) {
        return new School(class1, student100);
    }
}