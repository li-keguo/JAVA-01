# Redis 集群和高可用

## 概念介绍
### 主从复制-redis-cluter
- 当一个 master 实例和一个 slave 实例连接正常时， master 会发送一连串的命令流来保持对 slave 的更新，以便于将自身数据集的改变复制给 slave ， ：包括客户端的写入、key 的过期或被逐出等等。
- 当 master 和 slave 之间的连接断开之后，因为网络问题、或者是主从意识到连接超时， slave 重新连接上 master 并会尝试进行部分重同步：这意味着它会尝试只获取在断开连接期间内丢失的命令流。
- 当无法进行部分重同步时， slave 会请求进行全量重同步。这会涉及到一个更复杂的过程，例如 master 需要创建所有数据的快照，将之发送给 slave ，之后在数据集更改时持续发送命令流到 slave

每一个 Redis master 都有一个 replication ID ：这是一个较大的伪随机字符串，标记了一个给定的数据集。每个 master 也持有一个偏移量，master 将自己产生的复制流发送给 slave 时，发送多少个字节的数据，自身的偏移量就会增加多少，目的是当有新的操作修改自己的数据集时，它可以以此更新 slave 的状态。复制偏移量即使在没有一个 slave 连接到 master 时，也会自增，所以基本上每一对给定的

> Replication ID, offset

都会标识一个 master 数据集的确切版本。

当 slave 连接到 master 时，它们使用 PSYNC 命令来发送它们记录的旧的 master replication ID 和它们至今为止处理的偏移量。通过这种方式， master 能够仅发送 slave 所需的增量部分。但是如果 master 的缓冲区中没有足够的命令积压缓冲记录，或者如果 slave 引用了不再知道的历史记录（replication ID），则会转而进行一个全量重同步：在这种情况下， slave 会得到一个完整的数据集副本，从头开始。

下面是一个全量同步的工作细节：

master 开启一个后台保存进程，以便于生产一个 RDB 文件。同时它开始缓冲所有从客户端接收到的新的写入命令。当后台保存完成时， master 将数据集文件传输给 slave， slave将之保存在磁盘上，然后加载文件到内存。再然后 master 会发送所有缓冲的命令发给 slave。这个过程以指令流的形式完成并且和 Redis 协议本身的格式相同。

你可以用 telnet 自己进行尝试。在服务器正在做一些工作的同时连接到 Redis 端口并发出 [SYNC](https://redis.io/commands/sync) 命令。你将会看到一个批量传输，并且之后每一个 master 接收到的命令都将在 telnet 回话中被重新发出。事实上 SYNC 是一个旧协议，在新的 Redis 实例中已经不再被使用，但是其仍然向后兼容：但它不允许部分重同步，所以现在 **PSYNC** 被用来替代 SYNC。

之前说过，当主从之间的连接因为一些原因崩溃之后， slave 能够自动重连。如果 master 收到了多个 slave 要求同步的请求，它会执行一个单独的后台保存，以便于为多个 slave 服务

### Sentlinel机制保证高可用
Redis 的 Sentinel 系统用于管理多个 Redis 服务器（instance）， 该系统执行以下三个任务：
监控（Monitoring）： Sentinel 会不断地检查你的主服务器和从服务器是否运作正常。

- 提醒（Notification）： 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。
- 自动故障迁移（Automatic failover）： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器； 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。
Redis Sentinel 是一个分布式系统， 你可以在一个架构中运行多个 Sentinel 进程（progress）， 这些进程使用流言协议（gossip protocols)来接收关于主服务器是否下线的信息， 并使用投票协议（agreement protocols）来决定是否执行自动故障迁移， 以及选择哪个从服务器作为新的主服务器。
虽然 Redis Sentinel 释出为一个单独的可执行文件 redis-sentinel ， 但实际上它只是一个运行在特殊模式下的 Redis 服务器， 你可以在启动一个普通 Redis 服务器时通过给定 –sentinel 选项来启动 Redis Sentinel 

sentinel实现了raft协议保证集群高可用：

Raft首先选举出一个server作为Leader，然后赋予它管理日志的全部责任。Leader从客户端接收日志条目，复制给其他server，并告诉他们什么时候可以安全的将日志条目应用到自己的状态机上。拥有一个Leader可以简化replicated log的管理。例如，leader可以决定将新的日志条目放在什么位置，而无需询问其他节点，数据总是简单的从leader流向其他节点。Leader可能失败或者断开连接，这种情况下会选出一个新的leader。

通过leader，Raft将一致性问题分解成三个相当独立的子问题：

- Leader Election：当集群启动或者leader失效时必须选出一个新的leader。
- Log Replication：leader必须接收客户端提交的日志，并将其复制到集群中的其他节点，强制其他节点的日志与leader一样。
- Safety：最关键的安全点就是图3.2中的State Machine Safety Property。如果任何一个server已经在它的状态机apply了一条日志，其他的server不可能在相同的index处apply其他不同的日志条目。后面将会讲述raft如何实现这一点。

raft论文介绍

https://docs.qq.com/doc/DY0VxSkVGWHFYSlZJ

### 搭建过程

配置概览

```properties
master ip:127.0.0.1 port:6379  
slave1 ip:127.0.0.1 port:6380  
slave2 ip:127.0.0.1 port:6381  
sentinel1 ip:127.0.0.1 port:16379
sentinel2 ip:127.0.0.1 port:16380
sentinel3 ip:127.0.0.1 port:16381
```

新建文件夹sentlinel-cluter

```shell
mkdir sentinel-cluter
```

复制配置文件多个，每个代表一个节点

```shell
sudo cp redis.config sentinel-cluter/redis-6379.config
sudo cp redis.config sentinel-cluter/redis-6380.config
sudo cp redis.config sentinel-cluter/redis-6381.config

sudo cp sentinel.config sentinel-cluter/sentinel-16379.config
sudo cp sentinel.config sentinel-cluter/sentinel-16380.config
sudo cp sentinel.config sentinel-cluter/sentinel-16381.config
```

配置文件内容如下

```properties
# ------ redis config
# 6379
bind 127.0.0.1
protected-mode no
port 6379
tcp-backlog 511
timeout 0
tcp-keepalive 0
loglevel notice
logfile “”
databases 16
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir ./redis-6379
masterauth 15144
slave-serve-stale-data yes
slave-read-only yes
repl-diskless-sync no
repl-diskless-sync-delay 5
repl-disable-tcp-nodelay no
repl-backlog-size 1mb
slave-priority 100
requirepass 15144
appendonly yes
appendfilename “appendonly.aof”
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
lua-time-limit 5000
slowlog-log-slower-than 10000
slowlog-max-len 128
latency-monitor-threshold 0
notify-keyspace-events “”
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-size -2
list-compress-depth 0
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
hll-sparse-max-bytes 3000
activerehashing yes
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
hz 10
aof-rewrite-incremental-fsync yes
# 6380
... ...
bind 127.0.0.1
slaveof 127.0.0.1 6379
port 6380
... ...

# 6381
... ...
bind 127.0.0.1
slaveof 127.0.0.1 6379
port 6381
... ...
# sentinel config
# sentinel config 除了post不一样其他配置基本一致
... ...
port 16379
sentinel monitor mymaster 127.0.0.1 6379 2   
sentinel down-after-milliseconds mymaster 60000  
sentinel parallel-syncs mymaster 1  
sentinel failover-timeout mymaster 180000  
sentinel auth-pass mymaster 15144
... ...
```

启动

```bash
redis-server redis-6379.config 
redis-server redis-6380.config 
redis-server redis-6381.config 

redis-server sentinel-16379.config --sentinel
redis-server sentinel-16380.config --sentinel
redis-server sentinel-16381.config --sentinel
```

主从测试

```bash
redis-cli -p 6379
set master-test xxx
exit

redis-cli -p 6380
keys master-test
xxx
exit

redis-cli -p 6381
keys master-test
xxx
set salave-test xxx # error
exit

```

raft协议选举测试

```bash
kill redis-6379 # 此处为表意明确
redis-cli -p 6380 # 或者 6381
info # 找到重新选举的 master

# 重新主从测试
... ...
```

