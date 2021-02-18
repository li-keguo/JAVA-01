package cn.leaf.exercise.assembled.xml;

import cn.leaf.exercise.assembled.common.AssembledBean;
import cn.leaf.exercise.assembled.common.XmlBeanProperties;
import lombok.RequiredArgsConstructor;

/**
 * xml 无属性的 bean
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 12:33
 */
@RequiredArgsConstructor
public class XmlConstructPropertiesBean implements AssembledBean {

    private final XmlBeanProperties properties;

    @Override
    public void show() {
        System.out.println("我是通过 xml配置方式装配的 XmlConstructPropertiesBean " + properties);
    }
}
