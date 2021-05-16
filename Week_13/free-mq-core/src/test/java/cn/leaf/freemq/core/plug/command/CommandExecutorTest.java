package cn.leaf.freemq.core.plug.command;

import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import cn.leaf.freemq.core.plug.command.core.CommandExecutor;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;
import java.util.List;
import lombok.ToString;

/**
 * @author Liutianyou
 * @date 2021/5/9 12:00 上午
 */
class CommandExecutorTest {

  public static void main(String[] args) {
    CommandExecutor<String> commandExecutor = new CommandExecutor("Hello world");
    commandExecutor.addCommandProcessor(new TestCommand());
    try {
      commandExecutor.execute("test   --page 15 -n zs ls ww -f -name zs -o 10 9 8 etc ");
//      commandExecutor.execute("test -h");
    } catch (Exception e) {
      e.printStackTrace();
    }


  }




}
@ToString
class TestCommand implements CommandProcessor<String> {

  public TestCommand() {
  }

  @CommandParam(name = "p", alias = {"-page"},helpInfo = "输入页码")
  private Integer page;

  //    @CommandParam(name = "")
//    private String fe;
  @CommandParam(name = "name",helpInfo = "输入名称")
  private String name;
  @CommandParam(name = "h", helpInfo = "查看帮助",isHelp = true)
  private String help;

  @CommandParam(name = "n", helpInfo ="输入好多名称",valueCount = 3)
  private List<String> names;

  @CommandParam(name = "f")
  private boolean flag;
  @CommandParam(name = "o", valueCount = 3)
  private List<Integer> odd;

  @CommandParam(name = "",valueCount = 3)
  private List values;



  @Override
  public void process(String s) {
    System.out.println(this);

  }

  @Override
  public String getName() {
    return "test";
  }

  @Override
  public String getCommandHelpInfo() {
    return null;
  }


}