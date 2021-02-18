package cn.leaf.exercise.assembled.xml;

import cn.leaf.exercise.assembled.common.AssembledBean;

/**
 * xml 无属性的 bean
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 12:33
 */
public class XmlNoPropertiesBean implements AssembledBean {
    @Override
    public void show() {
        System.out.println("我是通过 xml配置方式装配的 XmlNoPropertiesBean");
    }
}
