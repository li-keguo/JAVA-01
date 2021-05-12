package cn.leaf.freemq.core.plug.command.exception;

import javafx.beans.NamedArg;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Data;
import lombok.Getter;

/**
 * @author Liutianyou
 * @date 2021/5/8 11:39 下午
 */
@Getter
public class NoSupportParamException extends BaseCommandParseException {
  private final String commandName;
  private final String paramName;

  public NoSupportParamException( String commandName, String paramName) {
    super("Command:["+commandName+"] not support param:["+paramName+"]");
    this.commandName = commandName;
    this.paramName = paramName;
  }

}
