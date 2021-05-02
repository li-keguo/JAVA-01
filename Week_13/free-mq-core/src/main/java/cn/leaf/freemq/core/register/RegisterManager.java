package cn.leaf.freemq.core.register;

import cn.leaf.freemq.model.FmqDataKey;

import java.util.List;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 10:40
 */
public interface RegisterManager {

    /**
     * get listener (connected)
     *
     * @param key listener key
     * @return listeners
     */
    List<ClientListenerConnection> getListenerConnection(FmqDataKey key);

}
