package cn.leaf.freemq.core.plug.command_line_plugin.process;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;

/**
 * @author Liutianyou
 * @date 2021/5/9 2:33 下午
 */
public class ExitCommandProcessor implements CommandProcessor<FmqBroker> {
  @Override
  public void process(FmqBroker context) {
    context.shutdown();
  }

  @Override
  public String getName() {
    return "exit";
  }
}
