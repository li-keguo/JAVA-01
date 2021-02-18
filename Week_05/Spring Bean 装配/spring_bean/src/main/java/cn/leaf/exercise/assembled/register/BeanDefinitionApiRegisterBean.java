package cn.leaf.exercise.assembled.register;

import cn.leaf.exercise.assembled.common.AssembledBean;

/**
 * BeanDefinitionApiRegisterBean
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 20:43
 */
public class BeanDefinitionApiRegisterBean implements AssembledBean {
    @Override
    public void show() {
        System.out.println("我是通过 调用BeanDefinition api的方式装配的 BeanDefinitionApiRegisterBean ");
    }
}
