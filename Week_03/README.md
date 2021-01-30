### 作业总览

1.（必做）整合你上次作业的 httpclient/okhttp；

2.（选做）使用 netty 实现后端 http 访问（代替上一步骤）

**周日作业题目：**（要求提交必做题目）

1.（必做）实现过滤器。

2.（选做）实现路由。

3.（选做）跑一跑课上的各个例子，加深对多线程的理解

4.（选做）完善网关的例子，试着调整其中的线程池参数

##### 概述

完成模块：

- service：service1,service2,service3, 老师提供的三个服务端
- client：上周作业的客户端发送器
- getaway：实现网关

网关实现：路由，负载、过滤器，代理转发

测试：

case1：

```properties
service1=http://localhost:8801
service2=http://localhost:8802
service3=http://localhost:8803
baidu=http://www.baidu.com
```

使用默认路由配置，四个网关代理客户端地址，分别对应着网关url/serviceName的地址

```
command ->: c :8888/service1

hello,nio! I'm service01

command ->: c :8888/service2

hello,nio! I'm service02

command ->: c :8888/baidu

<!DOCTYPE html>...
```

可以看到调整url值可访问不同的服务端程序

case2：

```properties
service1=http://localhost:8801,http://localhost:8802,http://localhost:8803

```

使用多服务负载路由配置，四个网关代理客户端地址，分别对应着网关url/serviceName的地址

```
command ->: c :8888/service1

hello,nio! I'm service01

command ->: c :8888/service1

hello,nio! I'm service02

command ->: c :8888/service1

hello,nio! I'm service03
```

可以看到访问同一个url可按顺序访问到三个客户端应用