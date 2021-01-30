package cn.leaf.exercise.nio.gateway.forwarder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * httpclent 实现,目前只支持get和post简单请求
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 21:12
 * @description httpclient 实现
 */
@Slf4j
public class HttpClientForwarderImpl implements HttpForwarder {
    private final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    @Override
    public GetawayResponse forward(HttpRequest originRequest, ChannelHandlerContext handlerContext, HttpRequest targetRequest) {
        if (HttpMethod.GET.equals(targetRequest.method())) {
            return exceptionHandler(() -> new GetawayResponse(HTTP_CLIENT.execute(new HttpGet(targetRequest.uri()))));

        }
        if (HttpMethod.POST.equals(targetRequest.method())) {
            return exceptionHandler(() -> new GetawayResponse(HTTP_CLIENT.execute(new HttpPost(targetRequest.uri()))));
        }
        return null;
    }




}
