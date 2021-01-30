package cn.leaf.exercise.nio.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {

    /**
     * 过滤器
     *
     * @param fullRequest 请求信息
     * @param ctx         通道管理器上下文信息
     */
    void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
