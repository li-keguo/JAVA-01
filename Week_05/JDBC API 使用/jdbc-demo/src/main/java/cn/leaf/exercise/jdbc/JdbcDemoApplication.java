package cn.leaf.exercise.jdbc;

import cn.leaf.exercise.jdbc.dao.UserDAO;
import cn.leaf.exercise.jdbc.dao.UserDAOImpl;
import cn.leaf.exercise.jdbc.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class JdbcDemoApplication implements ApplicationRunner {

    private final UserDAO userDAO;

    public static void main(String[] args) {
        SpringApplication.run(JdbcDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
//        System.out.println("case 1:insert");
//        insertTest1();
//        System.out.println("case 2:select");
//        selectTest1();
//        System.out.println("case 3:update");
//        updateTest1();
//        System.out.println("case 4:selectByLimit");
//        selectTest2();
//        System.out.println("case 5:selectByLimit");
//        selectTest3();

        System.out.println("case 5:inserts");
        insertTest2();

    }

    private void insertTest2() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            users.add(User.builder()
                    .id(7 + i)
                    .name("test1" + i)
                    .loginName("test1" + i)
                    .password("123" + i)
                    .build());
        }
        userDAO.inserts(users);
    }

    private void insertTest1() {
        User user = User.builder()
                .id(7)
                .name("test1")
                .loginName("test1")
                .password("123")
                .build();
        userDAO.insert(user);

    }

    private void updateTest1() {
        User user = User.builder()
                .id(7)
                .name("test1-update")
                .loginName("test1-update")
                .password("123-update")
                .build();
        userDAO.update(user);
    }

    private void selectTest1() {
        List<User> users = userDAO.selectAll();
        show(users);
    }

    private void selectTest2() {
        List<User> users = userDAO.selectByLimit(3);
        show(users);
    }

    private void selectTest3() {
        List<User> users = userDAO.selectByLimit(1, 2);
        show(users);
    }

    private void show(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
