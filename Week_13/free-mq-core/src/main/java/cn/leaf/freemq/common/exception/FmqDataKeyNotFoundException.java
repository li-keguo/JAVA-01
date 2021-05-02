package cn.leaf.freemq.common.exception;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project free-mq
 * @date 2021/4/28 15:10
 */
public class FmqDataKeyNotFoundException extends FmqException {
    public FmqDataKeyNotFoundException() {
        super();
    }

    public FmqDataKeyNotFoundException(String message) {
        super(message);
    }
}
