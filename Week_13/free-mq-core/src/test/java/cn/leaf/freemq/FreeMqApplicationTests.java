package cn.leaf.freemq;

import cn.leaf.freemq.core.FmqBroker;
import cn.leaf.freemq.core.FmqBrokerImpl;
import cn.leaf.freemq.core.context.DefaultFmqApplicationContextImpl;
import cn.leaf.freemq.core.plug.command_line_plugin.CommandLineManagementFmqPluginImpl;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class FreeMqApplicationTests {

    @Test
    void contextLoads() {
        Integer integer126_1= 126;
        Integer integer126_2= 126;
        int i126 = 126;
        Integer integer1288_1 = 1288;
        Integer integer1288_2 = 1288;
        int i1288 = 1288;
        System.out.println(integer126_1 == i126);
        System.out.println(integer126_1 == integer126_2);
        System.out.println(integer1288_1 == i1288);
        System.out.println(integer1288_1 == integer1288_2);
        System.out.println(integer1288_1.equals(integer1288_2));
        System.out.println(integer1288_2.equals(i1288));



    }

    public static void main(String[] args) {
        FmqBroker broker;
        broker = new FmqBrokerImpl("broker_01", DefaultFmqApplicationContextImpl.defaultFmqApplicationContext());

        broker.addPlug(new CommandLineManagementFmqPluginImpl());
        broker.run();

    }

}
