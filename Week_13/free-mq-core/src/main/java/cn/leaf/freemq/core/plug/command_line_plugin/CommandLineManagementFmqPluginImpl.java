package cn.leaf.freemq.core.plug.command_line_plugin;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.plug.FmqPlugin;
import cn.leaf.freemq.core.plug.command.core.CommandExecutor;
import cn.leaf.freemq.core.plug.command.exception.BaseCommandParseException;
import cn.leaf.freemq.core.plug.command_line_plugin.process.AddTopicCommandProcessor;
import cn.leaf.freemq.core.plug.command_line_plugin.process.ExitCommandProcessor;
import cn.leaf.freemq.core.plug.command_line_plugin.process.SendCommandProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Command line management
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/5/8 10:43
 */
@Slf4j
public class CommandLineManagementFmqPluginImpl implements FmqPlugin {

  CommandExecutor<FmqBroker> commandExecutor;

  @Override
  public void run(FmqBroker broker) {
    initCommandExecutor(broker);
    broker.getFmqApplicationContext()
        .threadFactory()
        .newThread(() -> commandManage(broker))
        .start();
  }

  void initCommandExecutor(FmqBroker broker) {
    commandExecutor = new CommandExecutor(broker);
    commandExecutor.addCommandProcessor(new SendCommandProcessor())
        .addCommandProcessor(new AddTopicCommandProcessor())
        .addCommandProcessor(new ExitCommandProcessor());
  }


  private void commandManage(FmqBroker broker) {
    log.info("plugin bash is running ... ... ");
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String command = scanner.nextLine();
      try{
        commandExecutor.execute(command);
      }catch (BaseCommandParseException exception){
        System.out.println(exception.getMessage());

      }

    }
  }

  @Override
  public void destroy(FmqBroker broker) {
    log.info("plugin bash is shutdown ... ... ");
  }

}
