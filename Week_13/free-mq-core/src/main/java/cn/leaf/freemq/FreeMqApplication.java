package cn.leaf.freemq;

import cn.leaf.freemq.core.FmqBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class FreeMqApplication implements ApplicationRunner {

    private final FmqBroker broker;

    public static void main(String[] args) {
        SpringApplication.run(FreeMqApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        broker.run();
    }




}
