package cn.leaf.exercise.nio.gateway.filter;

import cn.leaf.exercise.nio.gateway.HttpHandlerUtil;
import cn.leaf.exercise.nio.gateway.forwarder.GetawayResponse;
import cn.leaf.exercise.nio.gateway.forwarder.HttpForwarder;
import cn.leaf.exercise.nio.gateway.router.RouterConfig;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;


/**
 * 核心过滤器：负责处理路由分发
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 15:30
 * @description 核心过滤器
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
public class CoreHttpRequestFilterImpl implements HttpRequestFilter {
    private HttpForwarder forwarder;
    /**
     * url分隔符
     */
    public static final String URI_SPLIT_CODE = "/";
    /**
     * uri中获取约定的服务id 的位置，约定为第一个参数
     */
    public static final int SERVICE_ID_INDEX = 1;

    @Override
    public void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext handlerContext) {
        log.info("{}流量接口请求开始，时间为{}", handlerContext.name(), new Date());
        String uri = fullRequest.uri();
        log.info("接收到的请求url为{}", uri);
        String[] uriSplitArray = uri.split(URI_SPLIT_CODE);
        // 访问网关，欢迎
        if (uriSplitArray.length == 0) {
            welcomeResponse(fullRequest, handlerContext);
            ReferenceCountUtil.release(fullRequest);
            return;
        }
        String serviceAddress = RouterConfig.route(uriSplitArray[SERVICE_ID_INDEX]);
        // 未找到相关服务
        if (serviceAddress == null) {
            serviceNotFoundResponse(fullRequest, handlerContext, uriSplitArray[SERVICE_ID_INDEX]);
            ReferenceCountUtil.release(fullRequest);
            return;
        }
        String serviceUrl = serviceAddress + uri.replaceFirst(URI_SPLIT_CODE + uriSplitArray[SERVICE_ID_INDEX], "");
        log.info("转发请求url为{}", serviceUrl);
        DefaultFullHttpRequest targetRequest = new DefaultFullHttpRequest(fullRequest.protocolVersion(), fullRequest.method(), serviceUrl);
        HttpHandlerUtil.handler(fullRequest, handlerContext, c -> forwarder.forward(fullRequest, handlerContext, targetRequest));
        ReferenceCountUtil.release(fullRequest);
    }


    /**
     * 欢迎响应
     *
     * @param fullRequest    请求
     * @param handlerContext 上下文
     */
    private void welcomeResponse(FullHttpRequest fullRequest, ChannelHandlerContext handlerContext) {
        HttpHandlerUtil.handler(fullRequest, handlerContext, (context -> {
            String value = "hello! welcome ! I'm a simple getaway,my teacher is kimmking... no more";
            return new GetawayResponse(HttpHandlerUtil.defaultResponse(value, HttpResponseStatus.OK));
        }));
    }

    /**
     * 欢迎响应
     *
     * @param fullRequest    请求
     * @param handlerContext 上下文
     */
    private void serviceNotFoundResponse(FullHttpRequest fullRequest, ChannelHandlerContext handlerContext, String serviceId) {
        HttpHandlerUtil.handler(fullRequest, handlerContext, (context -> {
            String value = "not found proxy services is " + serviceId;
            return new GetawayResponse(HttpHandlerUtil.defaultResponse(value, HttpResponseStatus.NOT_FOUND));
        }));
    }


}
