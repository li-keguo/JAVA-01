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
 * TODO
 *
 * @author 李克国
 * @version 1.0.0
 * @project JAVA-01-client
 * @date 2021/1/19 10:32
 */
public class MyClient {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    public static void main(String[] args) throws IOException {
        String url = "http://127.0.0.1:8801";
//        String url = "http://www.baidu.com";
//        try (Response okHttpResponse = OK_HTTP_CLIENT.newCall(new Request.Builder()
//                .url(url)
//                .build())
//                .execute()) {
//            System.out.println(okHttpResponse);
//            System.out.println(Objects.requireNonNull(okHttpResponse.body()).string());
//        }
//        HttpResponse response = HTTP_CLIENT.execute(new HttpGet(url));
//        show(response);
        Executor executor = new Executor();
        executor.addService("okhttp", new OkHttpClientService(), "ok");
        executor.addService("httpclient", new HttpClientService(), "client", "c", "cl");
        executor.run();

    }

    static class Executor {
        private Map<String, HttpRequestService> services;

        public final String COMMAND_SPLITE_CODE = " ";
        public final String COMMAND_EXIT = "exit";
        private Scanner scanner;

        public void addService(String command, HttpRequestService service, String... alies) {
            if (services == null) {
                services = new HashMap<>();
            }
            services.put(command, service);
            for (String args : alies) {
                services.put(args, service);
            }
        }

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
                } catch (IOException e) {
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
            if (userInput == null || !userInput.contains(COMMAND_SPLITE_CODE)) {
                System.out.println(String.format("不合法的命令{%s}", userInput));
                return null;
            }
            return userInput.substring(0, userInput.indexOf(COMMAND_SPLITE_CODE));
        }

        private String getUrl(String userInput) {
            if (userInput == null || !userInput.contains(COMMAND_SPLITE_CODE)) {
                System.out.println(String.format("不合法的命令{%s}", userInput));
                return null;
            }
            String url = userInput.substring(userInput.indexOf(COMMAND_SPLITE_CODE)).trim();
            if (!url.startsWith("http://")) {
                url = "http://" + url;
            }
            return url;
        }
    }

    interface HttpRequestService extends Closeable {
        void send(String url) throws IOException;

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
        public void show() {
            HttpResponse response = currentHttpResponse.get();
            if (response != null) {
                System.out.println(response.getEntity().getContentType());
                System.out.println(response.getEntity().getContentLength());
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

    private static void show(HttpResponse response) {
        System.out.println(response.getEntity().getContentType());
        System.out.println(response.getEntity().getContentLength());
    }
}
