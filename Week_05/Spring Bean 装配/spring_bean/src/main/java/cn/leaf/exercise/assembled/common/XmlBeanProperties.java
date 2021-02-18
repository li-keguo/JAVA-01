package cn.leaf.exercise.assembled.common;

import lombok.Data;

/**
 * xml的方式装配bean的属性
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 12:15
 */
@Data
public class XmlBeanProperties {

    private String name;


    public void initialize() {
        name = "xml default";
    }

    @Override
    public String toString() {
        return "properties{" + "name='" + name + '\'' + '}';
    }
}
