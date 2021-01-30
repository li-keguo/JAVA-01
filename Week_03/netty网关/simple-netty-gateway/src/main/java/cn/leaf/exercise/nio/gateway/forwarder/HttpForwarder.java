package cn.leaf.exercise.nio.gateway.forwarder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 转发器
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 16:52
 * @description 转发器
 */
public interface HttpForwarder {

    /**
     * 转发
     *
     * @param originRequest  源请求
     * @param handlerContext 上下文
     * @param targetRequest  目标哦请求
     * @return response
     */
    GetawayResponse forward(HttpRequest originRequest, ChannelHandlerContext handlerContext, HttpRequest targetRequest);

    /**
     * 异常处理器
     *
     * @param functionHandler {@link Fun}
     * @return {@link GetawayResponse}
     */
    default GetawayResponse exceptionHandler(Fun functionHandler) {
        try {
            return functionHandler.exec();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    interface Fun {
        GetawayResponse exec() throws IOException;
    }
}
