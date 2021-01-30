package cn.leaf.exercise.nio.gateway.inbound;

import cn.leaf.exercise.nio.gateway.filter.CoreHttpRequestFilterImpl;
import cn.leaf.exercise.nio.gateway.filter.DefaultHttpRequestFilterHandlerImpl;
import cn.leaf.exercise.nio.gateway.filter.DefaultHttpRequestFilterImpl;
import cn.leaf.exercise.nio.gateway.forwarder.HttpClientForwarderImpl;
import cn.leaf.exercise.nio.gateway.forwarder.NettyHttpForwarderImpl;
import cn.leaf.exercise.nio.gateway.outbound.HttpInboundHandler;
import cn.leaf.exercise.nio.gateway.router.LoadBalancePropertiesHttpEndpointRouterImpl;
import cn.leaf.exercise.nio.gateway.router.RouterConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 管道初始化
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {


    public HttpInboundInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        // 添加过滤器链
        DefaultHttpRequestFilterHandlerImpl filterHandler = DefaultHttpRequestFilterHandlerImpl.builder().build();
        filterHandler.add(new DefaultHttpRequestFilterImpl());
//        filterHandler.add(new CoreHttpRequestFilterImpl(new HttpClientForwarderImpl()));
        filterHandler.add(new CoreHttpRequestFilterImpl(new NettyHttpForwarderImpl()));
        // 注册路由表
        RouterConfig.registerRouter(new LoadBalancePropertiesHttpEndpointRouterImpl());
        // 廷加核心通道处理器
        p.addLast(HttpInboundHandler.builder()
                .filterHandler(filterHandler)
                .build());

    }
}
