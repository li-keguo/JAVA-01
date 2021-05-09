package cn.leaf.freemq.model;

import org.springframework.context.Lifecycle;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project free-mq
 * @date 2021/4/28 11:13
 */
public interface FmqDataKey extends Lifecycle {

    /**
     * key
     *
     * @return key
     */
    String getKey();
}
