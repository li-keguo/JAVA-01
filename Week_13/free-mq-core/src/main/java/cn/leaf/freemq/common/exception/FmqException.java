package cn.leaf.freemq.common.exception;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project free-mq
 * @date 2021/4/28 13:55
 */
public class FmqException extends RuntimeException{

    public FmqException() {
        super();
    }

    public FmqException(String message) {
        super(message);
    }
}
