package cn.leaf.freemq.core.register;

import cn.leaf.freemq.model.FmqDataKey;

/**
 * 客户端监听连接
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 15:36
 */
public interface ClientListenerConnection {


    /**
     * is live
     *
     * @return is live
     */
    boolean isLive();

    /**
     * get listener key
     *
     * @return key
     */
    FmqDataKey getListenerKey();

    /**
     * get host
     *
     * @return host
     */
    String getHost();
}
