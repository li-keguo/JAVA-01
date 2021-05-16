## kafka集群配置



### kafka组件介绍

#### 主要组成

- producer 消息生产者
- consumer 消息消费者
- broker 经纪人
- topic 主体
- partition 消息分区

#### 详细说明

##### producer 

消息生产者，主要负责将消息体封装，打包放入指定的topic队列里，broker通过获取client端的消息发送请求，委派到producer生产消息，

##### consumer

消息消费者，主要负责消息消费，读取指定topic的消息，按照offset位置，向客户端推送消息，将消息标记为已消费或者释放内存空间。

##### broker

消息服务经纪人，服务器，或者控制器，主要负责处理消息读、写请求，存储消息，管理kafka中各个模型组件，使其按照指定的逻辑工作，启动时读取相关环境配置，按配置策略组织各个组件工作，数据管理等，并提供外部访问接口，支持和客户端的rpc调动，http调用等

##### topic

消息主题，可以看做已给队列，一个topic对应一个队列，发消息即为向这个队列里添加消息，消费即看作为读消息，

##### partition

在kafka中topic内部实现中为了支撑过多的数据量，更快的消费，抽象出partition群，一个topic中有多个partition，每一个partition能保证消息消费的有序性，类似于java中HashMap的结构，每个partition对应一个hash桶位置，消息消费时，可并行在不同partition中消费，互不影响，但提高了消息消费的处理能力，加上partition中对磁盘顺序读写的设计，使得kafka更加快速高效。

### 集群模式介绍

一个broker工作能力有限，为了更好的提供吞吐量，可用性等性能指标，那么就用一群broker工作，分散在各个机器上，利用多机器提供的计算处理能力，提高消息处理能力



### 集群搭建

#### 环境准备

- linux (OS)
- docker
- java （JDK 1.8）
- zk


#### 安装过程

下载

```http
http://kafka.apache.org/downloads.html
```

解压

启动zk

```dash
zookeeper-server-start.sh config/zookeeper.properties
```

启动kafka

```
kafka-server-start.sh config/server.properties
```

至此，kafka安装完成，下面将进行集群配置（伪集群，单机操作）

复制多份server.properties

```
server.properties
       broker.id=0
       listeners=PLAINTEXT://:9092
       log.dirs=/tmp/kafka-logs-0
       
server_1.properties
       broker.id=1
       listeners=PLAINTEXT://:9093
       log.dirs=/tmp/kafka-logs-1

server_2.properties
       broker.id=2
       listeners=PLAINTEXT://:9094
       log.dirs=/tmp/kafka-logs-2
```

启动多个kafka

```
bin/kafka-server-start.sh config/server.properties
bin/kafka-server-start.sh config/server_1.properties
bin/kafka-server-start.sh config/server_2.properties
```

kafka集群配置完成，真实使用时，将配置文件在不同机器上修改配置启动即可

