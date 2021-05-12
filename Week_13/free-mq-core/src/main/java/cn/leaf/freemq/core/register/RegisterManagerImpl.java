package cn.leaf.freemq.core.register;

import cn.leaf.freemq.model.FmqDataKey;

import java.util.List;

/**
 * @author 李克国
 * @date 2021/5/1
 */
public class RegisterManagerImpl implements RegisterManager {

    private List<ClientListenerConnection> connections;
    @Override
    public List<ClientListenerConnection> getListenerConnection(FmqDataKey key) {
        return connections;
    }
}
