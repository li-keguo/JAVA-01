package cn.leaf.freemq.core.plug.command.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liutianyou
 * @date 2021/5/8 10:31 下午
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface CommandParam {

  String name();
  int valueCount() default 1;
  String[] alias() default {};
  boolean isHelp() default  false;
  String helpInfo() default "";
  boolean isOptional() default true;
}
