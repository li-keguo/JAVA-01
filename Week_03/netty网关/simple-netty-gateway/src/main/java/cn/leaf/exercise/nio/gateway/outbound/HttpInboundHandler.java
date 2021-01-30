package cn.leaf.exercise.nio.gateway.outbound;

import cn.leaf.exercise.nio.gateway.filter.HttpRequestFilter;
import cn.leaf.exercise.nio.gateway.filter.HttpRequestFilterHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
@AllArgsConstructor
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    /**
     * 过滤器管理器
     */
    private HttpRequestFilterHandler filterHandler;

    public HttpInboundHandler() {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext handlerContext, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            List<HttpRequestFilter> filters = filterHandler.getFilters();
            for (HttpRequestFilter filter : filters) {
                filter.doFilter(fullHttpRequest,handlerContext);
            }
            return;
        }
        super.channelRead(handlerContext, msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }



}
