package cn.leaf.exercise.starter;

import cn.leaf.exercise.bean.Klass;
import cn.leaf.exercise.bean.School;
import cn.leaf.exercise.bean.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringbootStarterTestApplication implements ApplicationRunner {

    private final Klass klass;

    private final School school;

    private final Student student;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootStarterTestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(klass);
        System.out.println(school);
        System.out.println(student);
    }
}
