package cn.leaf.freemq.core.plug.command.entity;

import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Liutianyou
 * @date 2021/5/9 12:33 下午
 * 命令行帮助信息
 * @see CommandParam
 */
@Getter
@ToString
public class HelpInfo {

  private final String name;
  private final String[] alias;
  private final String info;
  private final int valueCount;


  public HelpInfo(CommandParam param) {
    this.alias = param.alias();
    this.info = param.helpInfo();
    this.valueCount = param.valueCount();
    this.name = "-"+param.name();

  }

}

