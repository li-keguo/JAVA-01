package cn.leaf.freemq.core.pool;

/**
 * Connectable
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/4/28 15:03
 */
public interface PoolConnectable {


    /**
     * connection to message queue pool
     *
     * @param pool message queue pool
     */
    void connect(FmqPool pool);
}
