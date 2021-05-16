package cn.leaf.freemq.core.plug.command.processor;

import cn.leaf.freemq.core.plug.command.core.CommandExecutor;
import cn.leaf.freemq.core.plug.command.entity.HelpInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liutianyou
 * @date 2021/5/8 10:41 下午
 */
public interface CommandProcessor<T> {

    /**
     * 命令的处理方法
     */
    void process(T context);

    /**
     * 当前命令的名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 输出帮助信息的默认方法
     *
     * @param helpInfos 帮助信息list
     */
    default void showHelp(List<HelpInfo> helpInfos) {
        helpInfos.forEach(System.out::println);
    }


    /**
     * 当前command 的 帮助信息
     *
     * @return 帮助者消息
     */
    String getCommandHelpInfo();

    /**
     * 回调当前的 executor;
     * @param executor
     */
    default void awareExecutor(CommandExecutor<T> executor) {
    }
}
