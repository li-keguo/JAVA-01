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

#### 串行GC



#### 并行GC



#### CMS



#### G1



### 结论

