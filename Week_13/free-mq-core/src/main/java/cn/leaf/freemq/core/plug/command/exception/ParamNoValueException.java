package cn.leaf.freemq.core.plug.command.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Liutianyou
 * @date 2021/5/8 11:52 下午
 */
@Getter
public class ParamNoValueException  extends BaseCommandParseException{
  private String commandName;
  private String paramName;

  public ParamNoValueException(String commandName,String paramName) {
    super("param:["+paramName+"] No value");
    this.commandName = commandName;
    this.paramName = paramName;
  }
}
