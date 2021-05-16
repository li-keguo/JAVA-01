package cn.leaf.freemq.core.plug.command_line_plugin.process;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;
import cn.leaf.freemq.model.FmqTopic;

/**
 * @author Liutianyou
 * @date 2021/5/9 2:27 下午
 */
public class AddTopicCommandProcessor implements CommandProcessor<FmqBroker> {

  @CommandParam(name="",isOptional = false)
  private String topic;
  @Override
  public void process(FmqBroker context) {
    context.addTopic(new FmqTopic(topic));
  }

  @Override
  public String getName() {
    return "addTopic";
  }

  @Override
  public String getCommandHelpInfo() {
    return "addTopic [name]";
  }
}
