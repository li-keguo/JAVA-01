package cn.leaf.freemq.core.plug.command.exception;

import lombok.Getter;

/**
 * @author Liutianyou
 * @date 2021/5/9 1:29 下午
 */
@Getter
public class ParamValueCountMismatchException extends BaseCommandParseException {
  private final int definedCount;
  private final int actualCount;
  private final String commandName;
  private final String paramName;

  public ParamValueCountMismatchException(int definedCount, int actualCount,
      String commandName, String paramName) {
    super("参数值数量不匹配");
    this.definedCount = definedCount;
    this.actualCount = actualCount;
    this.commandName = commandName;
    this.paramName = paramName;
  }
}
