package cn.leaf.exercise.assembled.xml;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * xml 资源配置
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/2/11 12:26
 */
@ImportResource(locations = {"/MATE-INFO/xml/xml-assembled-config.xml"})
@Configuration
public class XmlResoucesConfiguration {
}
