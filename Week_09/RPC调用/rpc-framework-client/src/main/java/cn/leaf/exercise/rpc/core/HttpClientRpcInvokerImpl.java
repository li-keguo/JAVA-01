package cn.leaf.exercise.rpc.core;

import com.alibaba.fastjson.JSONArray;
import lombok.SneakyThrows;
import netscape.javascript.JSObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * http client impl
 *
 * @author 李克国
 * @date 2021/3/19
 */
public class HttpClientRpcInvokerImpl implements RpcInvoker {

    private final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();

    @SneakyThrows
    @Override
    public RpcResponse invoke(RpcRequest request) {

        JSONArray params = new JSONArray(Arrays.stream(request.getArgs()).collect(Collectors.toList()));
        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(new StringEntity(params.toJSONString()));
        HttpResponse response = HTTP_CLIENT.execute(httpPost);

        return RpcResponse.builder().body(EntityUtils.toString(response.getEntity())).build();
    }
}
