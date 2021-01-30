package cn.leaf.exercise.nio.gateway;


import cn.leaf.exercise.nio.gateway.inbound.HttpInboundServer;

public class NettyServerApplication {

    public final static String GATEWAY_NAME = "simpleGetawayPort";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        int port = Integer.parseInt(System.getProperty("simpleGetawayPort", "8888"));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        try (HttpInboundServer server = new HttpInboundServer(port)) {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
