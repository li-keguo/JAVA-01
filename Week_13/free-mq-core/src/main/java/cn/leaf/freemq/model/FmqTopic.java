package cn.leaf.freemq.model;

import lombok.*;

/**
 * topic
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/26 19:43
 */
@Data
@Builder
//@AllArgsConstructor
@RequiredArgsConstructor
public class FmqTopic implements FmqDataKey {
    private final String topicName;

    @Override
    public String getKey() {
        return topicName;
    }
    // 是否在此存储 message list ？
}
