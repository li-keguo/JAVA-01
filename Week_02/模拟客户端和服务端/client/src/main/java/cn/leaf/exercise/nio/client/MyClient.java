package cn.leaf.exercise.nio.client;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

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

//        HttpResponse response = HTTP_CLIENT.execute(new HttpGet(url));
//        show(response);
        try (Response okHttpResponse = OK_HTTP_CLIENT.newCall(new Request.Builder()
                .url(url)
                .build())
                .execute()) {
            System.out.println(okHttpResponse);
        }

    }

    private static void show(HttpResponse response) {
        System.out.println(response.getEntity().getContentType());
        System.out.println(response.getEntity().getContentLength());
    }
}
