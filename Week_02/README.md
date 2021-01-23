## 作业总览



### 题目

1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。

2、使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar示例。

3、根据上述自己对于1和2的演示，写一段对于不同GC和堆内存的总结。

4、写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801

#### JVM总结

- 使用不同的jvm参数启动GCLogAnalysis.java，观察不同不同jvm的执行情况：

  - 相同堆内存下各个gc执行占用时间顺序为：G1<CMS<并行<串行（偶尔会有意外，总体来说是这样）
    - 在本次测试中无论多么不情愿，并行是表现最好的，可能是用GDK8的缘故，G1不够完善，并行是8的默认
  - 相同GC下，最大堆内存逐渐增加，性能成倒U型曲线，内存无限大不在考虑范围内
    - 年轻代逐渐增大，younggc频率成下降趋势，但fullgc可能会增加
    - 年轻代和老年代的比例要根据应用程序实际偏向调整比例

  使用测压工具sb对gateway-server-0.0.1-SNAPSHOT.jar在不同的jvm参数下的执行情况，分析最合适的堆大小设置，以支持程序在高并发的情况下性能（处理的请求数量）最好。

  - 随着堆内存增加，gc时间减少，请求处理数增加，
  - 堆内存增加到一定数量，请求数不在增加

JVM参数调整在不同的系统和环境中可能差异很大，需要多次实验和摸索，没有一陈不变的固定，只有随机应变的灵活

#### 客户端-服务端模拟

实现一个终端访问网站的小工具，可以指定使用httpclient或者okhttp访问指定的网站

实现核心代码如下：

```java
 public static void main(String[] args) throws IOException {
        Executor executor = new Executor();
        executor.addService("okhttp", new OkHttpClientService(), "ok", "o");
        executor.addService("httpclient", new HttpClientService(), "client", "c", "cl");
        executor.run();
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
          } catch (Exception e) {
              e.printStackTrace();
          }
          userInput = userInput();
      }
 }
// httpclient实现
public void send(String url) throws IOException {
     currentHttpResponse.set(HTTP_CLIENT.execute(new HttpGet(url)));
}
// okhttp 实现
public void send(String url) throws IOException {
      currentResponse.set(OK_HTTP_CLIENT.newCall(new Request.Builder()
            .url(url)
            .build())
            .execute());
}
```

测试结果如下：

```java
// 使用httpclient访问http://127.0.0.1:8801
command ->: c :8801
Content-Type:text/html;charset=utf-8
9
hello,nio
// 使用okhttp访问百度
command ->: ok www.baidu.com
Response{protocol=http/1.1, code=200, message=OK, url=http://www.baidu.com/}
<!DOCTYPE html>
<!--STATUS OK--><html> <head>...

```

