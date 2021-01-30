package cn.leaf.exercise.nio.gateway;

import cn.leaf.exercise.nio.gateway.forwarder.GetawayResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @Date 2021/1/30 21:19
 * @description TODO
 */
@Slf4j
public class HttpHandlerUtil {
    private HttpHandlerUtil() {
    }

    /**
     * 处理程序
     *
     * @param fullRequest    请求
     * @param handlerContext 上下文
     * @param expected       执行器
     */
    public static void handler(FullHttpRequest fullRequest, ChannelHandlerContext handlerContext, Function<ChannelHandlerContext, GetawayResponse> expected) {
        GetawayResponse response = null;
        try {
            response = expected.apply(handlerContext);
        } catch (Exception e) {
            log.error("响应出错", e);
            response = new GetawayResponse(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT));
        } finally {
            try {
                responsePostProcess(fullRequest, handlerContext, response);
            } catch (IOException e) {
                log.error("响应后置处理错误", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 默认响应
     *
     * @param value  响应内容
     * @param status {@link HttpResponseStatus}
     * @return {@link DefaultFullHttpResponse}
     */
    @NotNull
    public static DefaultFullHttpResponse defaultResponse(String value, HttpResponseStatus status) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
        response.headers().set("Content-Type", "application/json");
        response.headers().setInt("Content-Length", value.length());
        return response;
    }

    /**
     * 响应后置处理
     *
     * @param fullRequest    请求信息
     * @param handlerContext 上下文
     * @param response       响应信息
     */
    public static void responsePostProcess(FullHttpRequest fullRequest, ChannelHandlerContext handlerContext, GetawayResponse response) throws IOException {
        if (fullRequest != null) {
            if (!HttpUtil.isKeepAlive(fullRequest)) {
                handlerContext.write(response.get()).addListener(ChannelFutureListener.CLOSE);
            } else {
                assert response != null;
                HttpResponse httpResponse = response.get();
                httpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                handlerContext.write(httpResponse);
            }
        }
    }
}
