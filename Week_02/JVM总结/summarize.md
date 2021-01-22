## 使用GC（串行/并行/CMS/G1）对JVM调优总结

### 背景介绍

1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。

```java
public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("runing...");
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        while (System.currentTimeMillis() < endMillis) {
            Object garbage = generateGarbage(100*1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("over!create object count number is:" + counter.longValue());
    }
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}
```

2、使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar示例。

### 演示过程

**概述**：分别对GCLogAnalysis.java和gateway-server-0.0.1-SNAPSHOT.jar使用不同的GC启动，并对启动的堆内存大小进行设置，打印GC日志，根据GC日志分析GC执行情况，找到不同堆内存大小的对各个GC之间 的关系

##### 环境 

```
JDK8 
```

#### 串行GC

###### 测试一、



#### 并行GC



#### CMS



#### G1

#### gateway-server-0.0.1-SNAPSHOT.jar测试

###### 测试一

1,启动gateway-server-0.0.1-SNAPSHOT.jar

```vm
java -jar -Xmx152m -XX:NewRatio=2 .\gateway-server-0.0.1-SNAPSHOT.jar
```

2 ,测压

```log
PS  sb -u http://127.0.0.1:8088/api/hello -B specified -N60

20552   (RPS: 287)
Status 200:    20552
RPS: 336.2 (requests/second)
Max: 104ms
Min: 1ms
Avg: 1.2ms
  50%   below 1ms
  60%   below 1ms
  70%   below 1ms
  80%   below 1ms
  90%   below 2ms
  95%   below 2ms
  98%   below 3ms
  99%   below 3ms
99.9%   below 5ms
```

3,gc情况

```loh
S0C    S1C    S0U    S1U      EC       EU        OC         
0.0   1024.0  0.0    10.1  13312.0   1024.0   26624.0   
OU         MC       MU    CCSC   CCSU      YGC     YGCT  FGC
18237.5   36528.0 35803.0 4352.0 4073.2    253    0.759   0     
FGCT    CGC    CGCT     GCT
0.000   8      0.033    0.792
```

###### 测试二

1,启动gateway-server-0.0.1-SNAPSHOT.jar

```vm
java -jar -Xmx25m -XX:NewRatio=2 .\gateway-server-0.0.1-SNAPSHOT.jar
```

2 ,测压

```log
PS  sb -u http://127.0.0.1:8088/api/hello -B specified -N60
18309   (RPS: 255)
Status 200:    18309
RPS: 299.4 (requests/second)
Max: 611ms
Min: 1ms
Avg: 1.6ms
  50%   below 1ms
  60%   below 2ms
  70%   below 2ms
  80%   below 2ms
  90%   below 3ms
  95%   below 3ms
  98%   below 4ms
  99%   below 5ms
99.9%   below 8ms
```

3,gc情况

```loh
 S0C    S1C    S0U    S1U      EC       EU
 0.0   1024.0  0.0    18.4   8192.0   5120.0  
  OC         OU       MC      MU      CCSC   CCSU     YGC    
 17408.0    14657.9   35632.0 34867.2 4224.0 4011.7    119    
  YGCT    FGC    FGCT    CGC    CGCT     GCT
 0.428   3      0.224  84      0.243    0.895
```

###### 测试三

1,启动gateway-server-0.0.1-SNAPSHOT.jar

```vm
java -jar -Xmx1g -XX:NewRatio=2 .\gateway-server-0.0.1-SNAPSHOT.jar
```

2 ,测压

```log
PS sb -u http://127.0.0.1:8088/api/hello -B specified -N60

20485   (RPS: 286.2)
Status 200:    20485
RPS: 335.1 (requests/second)
Max: 84ms
Min: 1ms
Avg: 1.1ms
  50%   below 1ms
  60%   below 1ms
  70%   below 1ms
  80%   below 1ms
  90%   below 1ms
  95%   below 2ms
  98%   below 2ms
  99%   below 3ms
99.9%   below 4ms
```

3,gc情况

```loh
 S0C    S1C    S0U    S1U      EC       EU       
 0.0   1024.0  0.0    10.1  19456.0   6144.0 
  OC         OU        MC      MU     CCSC    CCSU    YGC     
 38912.0    22435.5   35632.0 35027.9 4224.0 4038.1     70   
  YGCT    FGC    FGCT    CGC    CGCT     GCT
 0.347   0      0.000   2      0.012    0.359
```



```
经三次测试数据来看（实际测试多次，取典型），随着堆内存增加，gc时间减少，请求处理书增加，但到一定程度达到瓶颈无法增加，测试过程中也出现一场数据，在较低内存分配情况下请求处理数到达2000，在此记录，
```



### 结论

