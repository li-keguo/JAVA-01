package cn.leaf.freemq.core.plug.command.exception;

import lombok.Getter;

/**
 * @author Liutianyou
 * @date 2021/5/8 11:11 下午
 */
@Getter
public class NoSuchCommandException extends BaseCommandParseException {
  String commandName;

  public NoSuchCommandException( String commandName) {
    super("No such command ["+commandName+"]");
    this.commandName = commandName;
  }
}
