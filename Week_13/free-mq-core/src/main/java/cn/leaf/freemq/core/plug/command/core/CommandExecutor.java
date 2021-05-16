package cn.leaf.freemq.core.plug.command.core;

import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Liutianyou
 * @date 2021/5/8 10:43 下午
 */
public class CommandExecutor<T> {

  private final Map<String, CommandProcessor<T>> processorMap;
  private final CommandParser<T> commandParser;
  private final T applicationContext;
  private final HashMap<String,String> applicationHelpInfo;



  public CommandExecutor(T applicationContext) {
    this.processorMap = new HashMap<>();
    this.commandParser = new CommandParser<T>();
    this.applicationContext = applicationContext;
    this.applicationHelpInfo = new HashMap<>();
  }

  /**
   * 执行命令
   *
   * @param command command
   */
  public void execute(String command) {
    Optional<CommandProcessor<T>> parse = commandParser.parse(command, processorMap);
    parse.ifPresent(processor -> {
      processor.awareExecutor(this);
      processor.process(applicationContext);

    });
  }

  /**
   * add A Process to CommandExecutor
   *
   * @param processor Class for process
   * @return self, convenient chain call
   */
  public CommandExecutor<T> addCommandProcessor(CommandProcessor<T> processor) {
    processorMap.put(processor.getName(), processor);
    applicationHelpInfo.put(processor.getName(),processor.getCommandHelpInfo());
    return this;
  }



  public HashMap<String, String> getApplicationHelpInfo() {
    return applicationHelpInfo;
  }
}
