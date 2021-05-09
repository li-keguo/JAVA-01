package cn.leaf.freemq.model;

import lombok.*;

/**
 * queue
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/26 16:19
 */
@Data
//@Builder
@RequiredArgsConstructor
public class FmqQueue implements FmqDataKey {

    private final String queueName;

    private volatile boolean running;


    @Override
    public String getKey() {
        return queueName;
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
