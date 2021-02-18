package cn.leaf.exercise.assembled.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 注解的方式装配bean的属性
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 12:15
 */
@Component
@Data
public class AnnotationBeanProperties {

    private String name;

    @PostConstruct
    public void initialize() {
        name = "annatation default";
    }

    @Override
    public String toString() {
        return "properties{" + "name='" + name + '\'' + '}';
    }
}
