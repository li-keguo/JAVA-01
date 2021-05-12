package cn.leaf.freemq.web;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test controller
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/5/8 14:22
 */
@RequestMapping("test/")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final FmqBroker broker;

    @RequestMapping("send/{topic}/{message}")
    public ResponseEntity<String> send(@PathVariable("topic") String topic, @PathVariable("message") String message) {
        broker.sendMessage(new FmqTopic(topic), new FmqMessage<>(message));
        return ResponseEntity.ok("ok");
    }
    @RequestMapping("topic/add/{topic}")
    public ResponseEntity<String> add(@PathVariable("topic") String topic) {
        broker.addTopic(new FmqTopic(topic));
        return ResponseEntity.ok("ok");
    }
}
