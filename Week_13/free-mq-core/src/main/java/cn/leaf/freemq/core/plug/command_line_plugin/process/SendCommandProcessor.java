package cn.leaf.freemq.core.plug.command_line_plugin.process;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;
import cn.leaf.freemq.model.FmqMessage;
import cn.leaf.freemq.model.FmqTopic;

/**
 * @author Liutianyou
 * @date 2021/5/9 2:13 下午
 */
public class SendCommandProcessor implements CommandProcessor<FmqBroker> {

  @CommandParam(name="t",alias = {"-topic"})
  private String topic;
  @CommandParam(name="m",alias = {"-message"})
  private String message;

  @Override
  public void process(FmqBroker broker) {
    FmqMessage<String> message = new FmqMessage<>();
    message.setBody(this.message);
    broker.sendMessage(new FmqTopic(topic), message);
  }

  @Override
  public String getName() {
    return "send";
  }
}
