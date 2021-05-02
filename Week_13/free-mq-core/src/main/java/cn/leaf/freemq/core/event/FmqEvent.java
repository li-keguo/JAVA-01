package cn.leaf.freemq.core.event;

import cn.leaf.freemq.core.context.FmqApplicationContext;
import cn.leaf.freemq.model.FmqDataKey;

/**
 * event
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 15:41
 */
public interface FmqEvent {

    /**
     * get application context
     *
     * @return application context
     */
    FmqApplicationContext getApplicationContext();

    /**
     * get data key
     * @return data key
     */
    FmqDataKey getDataKey();
}
