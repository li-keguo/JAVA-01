package cn.leaf.freemq.core.plug.command.core;


import cn.leaf.freemq.core.plug.command.annotation.CommandParam;
import cn.leaf.freemq.core.plug.command.entity.CommandParamWrapper;
import cn.leaf.freemq.core.plug.command.entity.HelpInfo;
import cn.leaf.freemq.core.plug.command.exception.NoSuchCommandException;
import cn.leaf.freemq.core.plug.command.exception.NoSupportParamException;
import cn.leaf.freemq.core.plug.command.exception.ParamNoValueException;
import cn.leaf.freemq.core.plug.command.exception.ParamValueCountMismatchException;
import cn.leaf.freemq.core.plug.command.exception.ParamValueTypeMismatchException;
import cn.leaf.freemq.core.plug.command.processor.CommandProcessor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Liutianyou
 * @date 2021/5/9 12:29 下午
 */
public class CommandParser<T> {

  private final String COMMAND_PARAM_PREFIX = "-";

  public Optional<CommandProcessor<T>> parse(String command,
      Map<String, CommandProcessor<T>> processorMap) {
    String[] split = command.split("\\s+");
    Iterator<String> iterator = Arrays.stream(split).iterator();
    String commandName = iterator.next();
    CommandProcessor<T> processor = processorMap.get(commandName);
    if (processor == null) {
      throw new NoSuchCommandException(commandName);
    }
    Class<? extends CommandProcessor<T>> processorClazz = (Class<? extends CommandProcessor<T>>) (processor
        .getClass());

    try {
      CommandProcessor<T> processorInstance = getDefaultConstructor(processorClazz).newInstance();
      Map<String, CommandParamWrapper> wrapperMap = getParam(processorClazz);
      while (iterator.hasNext()) {
        String next = iterator.next();
        String paramName = next.substring(1);
        if (next.startsWith(COMMAND_PARAM_PREFIX)) {
          if (!wrapperMap.containsKey(paramName)) {
            throw new NoSupportParamException(commandName, next);
          }
          if (wrapperMap.get(paramName).getAnnotation().isHelp()) {
            //收集Help信息，执行 showHelper 方法
            List<HelpInfo> infos = collectHelpInfo(processorClazz);
            processorInstance.showHelp(infos);
            return Optional.empty();
          }
          injectCommandValue(iterator, wrapperMap, processorInstance, commandName, next, true);
          continue;
        }
        injectCommandValue(iterator, wrapperMap, processorInstance, commandName, next, false);
      }
      return Optional.of(processorInstance);
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  void injectCommandValue(Iterator<String> iterator, Map<String, CommandParamWrapper> wrapperMap,
      CommandProcessor<T> processorInstance, String commandName, String paramName,
      boolean hasParamName)
      throws IllegalAccessException {
    CommandParamWrapper paramWrapper;
    paramWrapper = wrapperMap.get("");
    if (hasParamName) {
      paramWrapper = wrapperMap.get(paramName.substring(1));
    }
    int valueCount = paramWrapper.getAnnotation().valueCount();
    Field field = paramWrapper.getField();
    field.setAccessible(true);
    if (valueCount == 1) {
      injectSingleValue(field, processorInstance, iterator, commandName, paramName, hasParamName);
      return;
    }
    injectMultipleValue(field, valueCount, iterator, commandName, paramName, processorInstance,
        hasParamName);

  }

  private void injectSingleValue(Field field, CommandProcessor<T> processorInstance,
      Iterator<String> iterator, String commandName, String paramName, boolean hasParamName)
      throws IllegalAccessException {
    if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
      field.set(processorInstance, true);
      return;
    }
    if (!hasParamName) {
      field.set(processorInstance, field.getType().equals(String.class) ? paramName
          : convert(field.getType(), paramName, commandName, paramName));
      return;
    }

    if (!iterator.hasNext()) {
      throw new ParamNoValueException(commandName, paramName);
    }
    String paramValue = iterator.next();
    if (paramValue.startsWith(COMMAND_PARAM_PREFIX)) {
      throw new ParamNoValueException(commandName, paramValue);
    }
    field.set(processorInstance, field.getType().equals(String.class) ? paramValue
        : convert(field.getType(), paramValue, commandName, paramName));
  }

  /**
   * 获取当前默认构造器
   *
   * @param processorClazz Clazz
   * @return 默认构造器
   */
  Constructor<? extends CommandProcessor<T>> getDefaultConstructor(
      Class<? extends CommandProcessor<T>> processorClazz)
      throws NoSuchMethodException {

    Constructor<? extends CommandProcessor<T>> constructor = processorClazz
        .getDeclaredConstructor();
    constructor.setAccessible(true);
    return constructor;
  }

  /**
   * 为参数注入多值
   *
   * @param field             被注入的属性
   * @param valueCount        参数值数量
   * @param iterator          iterator
   * @param commandName       命令名称
   * @param paramName         参数名称
   * @param processorInstance 处理器实例
   * @param hasParamName      当前注入字段是否有参数名称
   * @throws IllegalAccessException IllegalAccessException
   */
  private void injectMultipleValue(Field field, Integer valueCount, Iterator<String> iterator,
      String commandName, String paramName, CommandProcessor<T> processorInstance,
      boolean hasParamName)
      throws IllegalAccessException {

    Class<?> genericType = getGenericType(field);
    //多值的情况
    List<Object> paramValues = new ArrayList<>();
    for (int i = 0; i < valueCount; i++) {
      String paramValue = "";
      if (!hasParamName) {
        if (i != 0) {
          if (!iterator.hasNext()) {
            throw new ParamValueCountMismatchException(valueCount, paramValues.size(), commandName,
                paramName);
          }
          paramValue = iterator.next();
        } else {
          paramValue = paramName;
        }
      } else {
        if (!iterator.hasNext()) {
          throw new ParamValueCountMismatchException(valueCount, paramValues.size(), commandName,
              paramName);
        }
        paramValue = iterator.next();
      }
      paramValues.add(genericType.equals(String.class) ? paramValue
          : convert(genericType, paramValue, commandName, paramName));
    }
    field.set(processorInstance, paramValues);


  }

  /**
   * 获取属性的泛型
   *
   * @param field 属性
   * @return 没有泛型 按照泛型是 String 处理
   */
  private Class<?> getGenericType(Field field) {
    Type genericType = field.getGenericType();
    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      return (Class<?>) pt.getActualTypeArguments()[0];
    }
    return String.class;
  }

  /**
   * 收集help 信息
   *
   * @param process 处理器
   * @return 帮助信息
   */
  public List<HelpInfo> collectHelpInfo(Class<? extends CommandProcessor<T>> process) {
    Map<String, CommandParamWrapper> param = getParam(process);
    List<CommandParamWrapper> collect = param.values()
        .stream()
        .distinct()
        .collect(Collectors.toList());
    return collect.stream()
        .map(p -> new HelpInfo(p.getAnnotation()))
        .collect(Collectors.toList());
  }

  /**
   * 对命令行参数 的 值进行类型转换
   *
   * @param targetType  目标类型
   * @param paramValue  参数值
   * @param commandName 命令的名称
   * @param paramName   参数名称
   * @return 转换后的结果
   */
  <TARGET> TARGET convert(Class<TARGET> targetType, String paramValue, String commandName,
      String paramName) {
    try {
      Method valueOf = targetType.getDeclaredMethod("valueOf", String.class);
      Object invoke = valueOf.invoke(targetType, paramValue);
      return (TARGET) invoke;
    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      throw new ParamValueTypeMismatchException(commandName, paramName, paramValue);
    }
  }

  /**
   * 获取 所有 命令行参数 名称及别名对应的 CommandParamWrapper;
   *
   * @param process 命令处理器
   * @return 名称/别名->CommandParamWrapper;
   */
  Map<String, CommandParamWrapper> getParam(Class<? extends CommandProcessor<T>> process) {
    Field[] declaredFields = process.getDeclaredFields();
    Map<String, CommandParamWrapper> fieldMap = new HashMap<>(64);
    List<Field> collect = Arrays.stream(declaredFields)
        .filter(this::canDealType)
        .collect(Collectors.toList());

    for (Field declaredField : collect) {
      CommandParam annotation = declaredField.getAnnotation(CommandParam.class);
      CommandParamWrapper wrapper = new CommandParamWrapper(declaredField, annotation);
      fieldMap.put(annotation.name(), wrapper);
      String[] alias = annotation.alias();
      for (String s : alias) {
        fieldMap.put(s, wrapper);
      }
    }
    return fieldMap;
  }

  /**
   * 判断当前字段是否需要按照参数名称注入值
   *
   * @param field 属性
   * @return 基础类型，基础类型的包装类，List，String 类型自动注入
   */
  private boolean canDealType(Field field) {
    try {
      Class<?> clazz = field.getType();
      if (field.getAnnotation(CommandParam.class) == null) {
        return false;
      }
      if (clazz.isPrimitive()) {
        return true;
      }
      if (clazz.equals(String.class) || List.class.isAssignableFrom(field.getType())) {
        return true;
      }
      if (((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive()) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }
}
