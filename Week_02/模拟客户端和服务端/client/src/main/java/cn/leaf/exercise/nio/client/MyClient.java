package cn.leaf.exercise.nio.client;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * 客户端
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01-client
 * @date 2021/1/19 10:32
 */
public class MyClient {

    public static void main(String[] args) throws IOException {
        Executor executor = new Executor();
        executor.addService("okhttp", new OkHttpClientService(), "ok", "o");
        executor.addService("httpclient", new HttpClientService(), "client", "c", "cl");
        executor.run();
    }

    static class Executor {
        private Map<String, HttpRequestService> services;
        public final String COMMAND_SPLIT_CODE = " ";
        public final String COMMAND_EXIT = "exit";
        public final String URL_PREFIX = "http://";
        private Scanner scanner;

        /**
         * 添加服务
         *
         * @param command 命令
         * @param service 服务
         * @param alies   别名
         */
        public void addService(String command, HttpRequestService service, String... alies) {
            if (services == null) {
                services = new HashMap<>();
            }
            services.put(command, service);
            for (String args : alies) {
                services.put(args, service);
            }
        }

        /**
         * 运行
         */
        public void run() {
            String userInput = userInput();
            while (!COMMAND_EXIT.equals(userInput)) {
                String command = getCommand(userInput);
                String url = getUrl(userInput);
                if (url == null) {
                    System.out.println("未知的访问地址");
                }
                try (HttpRequestService service = services.get(command)) {
                    if (service != null) {
                        service.send(url);
                        service.show();
                    } else {
                        System.out.println(String.format("未知的指令{%s}", command));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userInput = userInput();
            }
        }

        private String userInput() {
            if (scanner == null) {
                scanner = new Scanner(System.in);
            }
            System.out.print("\ncommand ->: ");
            return scanner.nextLine();
        }

        private String getCommand(String userInput) {
            if (userInput == null || !userInput.contains(COMMAND_SPLIT_CODE)) {
                System.out.println(String.format("不合法的命令{%s}", userInput));
                return null;
            }
            return userInput.substring(0, userInput.indexOf(COMMAND_SPLIT_CODE));
        }

        private String getUrl(String userInput) {
            if (userInput == null || !userInput.contains(COMMAND_SPLIT_CODE)) {
                System.out.println(String.format("不合法的命令{%s}", userInput));
                return null;
            }
            String url = userInput.substring(userInput.indexOf(COMMAND_SPLIT_CODE)).trim();
            if (url.startsWith(":")) {
                url = "127.0.0.1" + url;
            }
            if (!url.startsWith(URL_PREFIX)) {
                url = URL_PREFIX + url;
            }
            return url;
        }
    }

    interface HttpRequestService extends Closeable {
        /**
         * 发送请求
         *
         * @param url url地址
         * @throws IOException 读取异常
         */
        void send(String url) throws IOException;

        /**
         * 显示结果
         *
         * @throws IOException 读取异常
         */
        void show() throws IOException;
    }

    static class HttpClientService implements HttpRequestService {
        private final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();
        private final ThreadLocal<HttpResponse> currentHttpResponse = new ThreadLocal<>();

        @Override
        public void send(String url) throws IOException {
            currentHttpResponse.set(HTTP_CLIENT.execute(new HttpGet(url)));
        }

        @Override
        public void show() throws IOException {
            HttpResponse response = currentHttpResponse.get();
            if (response != null) {
                System.out.println(response.getEntity().getContentType());
                System.out.println(response.toString());
                System.out.println(response.getEntity().toString());
            } else {
                System.out.println("未有返回结果");
            }
        }

        @Override
        public void close() throws IOException {
            currentHttpResponse.remove();
        }
    }

    static class OkHttpClientService implements HttpRequestService {
        private final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
        private final ThreadLocal<Response> currentResponse = new ThreadLocal<>();

        @Override
        public void send(String url) throws IOException {
            currentResponse.set(OK_HTTP_CLIENT.newCall(new Request.Builder()
                    .url(url)
                    .build())
                    .execute());

        }

        @Override
        public void show() throws IOException {
            Response okHttpResponse = currentResponse.get();
            if (okHttpResponse != null) {
                System.out.println(okHttpResponse);
                System.out.println(Objects.requireNonNull(okHttpResponse.body()).string());
            } else {
                System.out.println("未有返回结果");
            }
        }

        @Override
        public void close() throws IOException {
            Response okHttpResponse = currentResponse.get();
            if (okHttpResponse != null) {
                okHttpResponse.close();
            }
            currentResponse.remove();
        }
    }
}
