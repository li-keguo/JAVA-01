package cn.leaf.freemq.core.plug;

import cn.leaf.freemq.core.FmqBroker;

/**
 * 插件
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/5/8 10:26
 */
public interface FmqPlugin {

    /**
     * run
     *
     * @param context application context
     */
    void run(FmqBroker context);

    /**
     * destroy
     *
     * @param context application context
     */
    void destroy(FmqBroker context);
}
