package cn.leaf.exercise.nio.gateway.forwarder;

import cn.hutool.core.io.IoUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.Data;
import okhttp3.Response;
import org.apache.http.Header;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 统一响应
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 11:38
 * @description 统一响应
 */
@Data
public class GetawayResponse {
    private HttpResponse nettyResponse;
    private Response okResponse;
    private org.apache.http.HttpResponse httpResponse;

    public GetawayResponse(HttpResponse nettyResponse) {
        this.nettyResponse = nettyResponse;
    }

    public GetawayResponse(Response okResponse) {
        this.okResponse = okResponse;
    }

    public GetawayResponse(org.apache.http.HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public HttpResponse get() throws IOException {
        if (nettyResponse != null) {
            return nettyResponse;
        }
        if (httpResponse != null) {
            return convert(httpResponse);
        }
        if (okResponse != null) {
            return convert(okResponse);
        }
        return null;
    }

    private HttpResponse convert(Response okResponse) {
        String content = IoUtil.read(Objects.requireNonNull(okResponse.body()).charStream());
        DefaultFullHttpResponse response = defaultResponse(content, HttpResponseStatus.OK);

        Map<String, List<String>> multimap = okResponse.headers().toMultimap();
        multimap.forEach((k, v) -> {
            System.out.println(v);
        });
        return response;
    }

    private HttpResponse convert(org.apache.http.HttpResponse httpResponse) throws IOException {
        String content = IoUtil.read(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        DefaultFullHttpResponse response = defaultResponse(content, HttpResponseStatus.OK);
        Header[] headers = httpResponse.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header);
            response.headers().set(header.getName(), header.getValue());
        }
        return response;
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
}