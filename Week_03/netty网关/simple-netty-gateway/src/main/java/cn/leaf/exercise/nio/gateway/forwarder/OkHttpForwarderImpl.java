package cn.leaf.exercise.nio.gateway.forwarder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * ok http 实现
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @Date 2021/1/30 21:35
 * @description ok http 实现
 */
public class OkHttpForwarderImpl implements HttpForwarder {

    private final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    @Override
    public GetawayResponse forward(HttpRequest originRequest, ChannelHandlerContext handlerContext, HttpRequest targetRequest) {
        return exceptionHandler(() -> new GetawayResponse(OK_HTTP_CLIENT.newCall(new Request.Builder()
                .url(targetRequest.uri())
                .method(targetRequest.method().name(), null)
                .build())
                .execute()));

    }
}
