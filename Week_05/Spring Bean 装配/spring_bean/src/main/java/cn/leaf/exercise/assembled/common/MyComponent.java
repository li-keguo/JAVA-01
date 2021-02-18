package cn.leaf.exercise.assembled.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author 李克国
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent {


    @AliasFor(annotation = Component.class)
    String value() default "";
}