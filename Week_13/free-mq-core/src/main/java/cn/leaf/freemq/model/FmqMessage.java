package cn.leaf.freemq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;

/**
 * mq message
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/26 15:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FmqMessage<T> {

    private Map<String,Object> headers;

    private T body;
}
