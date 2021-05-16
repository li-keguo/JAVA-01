package cn.leaf.freemq.core.plug.command.entity;

import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import java.lang.reflect.Field;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Liutianyou
 * @date 2021/5/8 11:28 下午
 */
@Data
@AllArgsConstructor
public class CommandParamWrapper {
  private Field field;
  private CommandParam annotation;
  private Object value=null;



}
