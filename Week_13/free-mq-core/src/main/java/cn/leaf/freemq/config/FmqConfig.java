package cn.leaf.freemq.config;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.FmqBrokerImpl;
import cn.leaf.freemq.core.context.DefaultFmqApplicationContextImpl;
import cn.leaf.freemq.core.plug.command_line_plugin.CommandLineManagementFmqPluginImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project free-mq
 * @date 2021/4/26 18:58
 */
@Configuration
public class FmqConfig {

    @Bean
    public FmqBroker broker() {
        FmqBroker broker = new FmqBrokerImpl("broker_01", DefaultFmqApplicationContextImpl.defaultFmqApplicationContext());
        broker.addPlug(new CommandLineManagementFmqPluginImpl());
        return broker;
    }
}
