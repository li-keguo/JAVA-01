package cn.leaf.freemq.core.plug.command.exception;

import lombok.Getter;

/**
 * @author Liutianyou
 * @date 2021/5/9 1:16 上午
 */
@Getter
public class ParamValueTypeMismatchException extends BaseCommandParseException{
  String commandName;
  String paramName;
  String paramValue;

  public ParamValueTypeMismatchException(String commandName, String paramName,
      String paramValue) {
    super("The parameter "+paramName+" of the command ["+commandName+"] does not support a value of "+paramValue);
    this.commandName = commandName;
    this.paramName = paramName;
    this.paramValue = paramValue;
  }
}
