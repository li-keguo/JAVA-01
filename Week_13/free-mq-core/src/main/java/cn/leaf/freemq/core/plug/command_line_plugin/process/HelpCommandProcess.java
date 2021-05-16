package cn.leaf.freemq.core.plug.command_line_plugin.process;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import cn.leaf.freemq.core.plug.command.core.CommandExecutor;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;

import java.util.HashMap;

/**
 * @author Liutianyou
 * @date 2021/5/9 2:45 下午
 */
public class HelpCommandProcess implements CommandProcessor<FmqBroker> {
    private CommandExecutor<FmqBroker> executor;

    @Override
    public void process(FmqBroker context) {
        HashMap<String, String> applicationHelpInfo =
                this.executor.getApplicationHelpInfo();
        applicationHelpInfo.forEach((key,value)->{
            System.out.println(key+"\t\t"+value);
        });

    }


    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void awareExecutor(CommandExecutor<FmqBroker> executor) {
        this.executor = executor;
    }

    @Override
    public String getCommandHelpInfo() {
        return "show help";
    }
}
