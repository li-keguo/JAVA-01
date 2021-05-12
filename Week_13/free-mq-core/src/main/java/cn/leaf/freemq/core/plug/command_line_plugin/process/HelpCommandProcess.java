package cn.leaf.freemq.core.plug.command_line_plugin.process;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;

/**
 * @author Liutianyou
 * @date 2021/5/9 2:45 下午
 */
public class HelpCommandProcess implements CommandProcessor<FmqBroker> {

  @Override
  public void process(FmqBroker context) {
//    System.out.println("add");
  }

  @Override
  public String getName() {
    return "help";
  }
}
