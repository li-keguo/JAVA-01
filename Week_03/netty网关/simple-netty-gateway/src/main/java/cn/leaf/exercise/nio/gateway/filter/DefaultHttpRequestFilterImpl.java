package cn.leaf.exercise.nio.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认过滤器
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @Date 2021/1/29 21:55
 * @description 默认过滤器
 */
@Slf4j
public class DefaultHttpRequestFilterImpl implements HttpRequestFilter {
    @Override
    public void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        log.info("我是DefaultHttpRequestFilter，你经过了我这里，我什么都没做 ...");
    }
}
