package cn.leaf.exercise.nio.gateway.forwarder;

import cn.leaf.exercise.nio.gateway.util.HttpHandlerUtil;
import cn.leaf.exercise.nio.gateway.util.NamedThreadFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;

import java.util.concurrent.*;

/**
 * netty实现
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01
 * @date 2021/1/30 22:50
 * @description netty实现
 */
public class NettyHttpForwarderImpl implements HttpForwarder {
    /**
     * 保持时间
     */
    public static final long KEEP_ALIVE_TIME = 1000;
    /**
     * 队列大小
     */
    public static final int QUEUE_SIZE = 2048;

    /**
     * 连接超时时长
     */
    public static final int CONNECT_TIME_OUT_TIME = 1000;

    /**
     * 超时时长
     */
    public static final int SO_TIME_OUT_TIME = 1000;

    private CloseableHttpAsyncClient httpclient;

    private ExecutorService proxyService;

    public NettyHttpForwarderImpl() {
        initialize();
    }

    public void initialize() {
        int cores = Runtime.getRuntime().availableProcessors() * 2;

        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(QUEUE_SIZE),
                new NamedThreadFactory("netty forwarder"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT_TIME)
                .setSoTimeout(SO_TIME_OUT_TIME)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        httpclient.start();
    }

    @Override
    public GetawayResponse forward(HttpRequest originRequest, ChannelHandlerContext handlerContext, HttpRequest targetRequest) {
        proxyService.submit(() -> {
            final HttpGet httpGet = new HttpGet(targetRequest.uri());
            httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
            httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(final HttpResponse endpointResponse) {
                    HttpHandlerUtil.handler(originRequest, handlerContext, c -> new GetawayResponse(endpointResponse));
                }

                @Override
                public void failed(final Exception ex) {
                    httpGet.abort();
                    ex.printStackTrace();
                }

                @Override
                public void cancelled() {
                    httpGet.abort();
                }
            });
        });
        return new GetawayResponse();
    }
}
