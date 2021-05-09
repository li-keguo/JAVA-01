package cn.leaf.freemq.core.plug.command.exception;

/**
 * @author Liutianyou
 * @date 2021/5/9 2:57 下午
 */
public class BaseCommandParseException extends RuntimeException {

  public BaseCommandParseException(String message) {
    super(message);
  }
}
