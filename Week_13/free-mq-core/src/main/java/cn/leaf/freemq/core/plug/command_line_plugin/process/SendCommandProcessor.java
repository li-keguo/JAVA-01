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

  @CommandParam(name="t",alias = {"-topic"},isOptional = false)
  private String topic;
  @CommandParam(name="m",alias = {"-message"},isOptional = false)
  private String message;
  @CommandParam(name="h",alias = {"-help"},isHelp = true)
  private String help;
  @CommandParam(name="n",alias = {"-count"})
  private Integer count=1;
  @Override
  public void process(FmqBroker broker) {
    int successCount = 0;
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      FmqMessage<String> message = new FmqMessage<>();
      message.setBody(this.message);
      boolean b = broker.sendMessage(new FmqTopic(topic), message);
      if(b){
        successCount++;
      }
    }
    if(count>=1){
      long cost = System.currentTimeMillis()-startTime;
      double sps = successCount / (cost / 1000.0);
      System.out.println("total:\t"+count);
      System.out.println("success:\t"+successCount);
      System.out.println("fail:\t"+(count-successCount));
      System.out.printf("SPS(send message per second):%.2f",sps);
    }

  }

  @Override
  public String getName() {
    return "send";
  }

  @Override
  public String getCommandHelpInfo() {
    return "-t [topicName] -m [content]";
  }
}
