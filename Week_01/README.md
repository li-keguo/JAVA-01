## 作业总览



### 题目

1. 作业：画一下 Xmx、Xms、Xmn、Meta、 DirectMemory、Xss 这些内存参数的关系
2. 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法， 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供



#### 题目一

![avatar](https://raw.githubusercontent.com/li-keguo/JAVA-01/main/Week_01/JVM%E5%86%85%E5%AD%98%E5%8F%82%E6%95%B0%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/JVM%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B.png)



#### 作业二

采用两种种方式实现，读文件和读base64编码字符串

```java
        // 读取base64加密的.class字符串 执行hello方法
        test(new Base64HelloClassLoader(SUFFIX_CLASS));
        // 读取base64加密的.xlass字符串 执行hello方法
        test(new Base64HelloClassLoader(SUFFIX_XLASS));

        String classPath = System.getProperty("user.dir") + File.separator + "Hello";
        // 读取.class文件 执行hello方法
        test(new FileHelloClassLoader(SUFFIX_CLASS, classPath));
        // 读取.xlass文件 执行hello方法
        test(new FileHelloClassLoader(SUFFIX_XLASS, classPath));
```

#### 笔记

##### 栈

栈内存归属于单个线程，每个线程都会有一个栈内存，其存储的变量只能在其所属线程中可见，即栈内存可以理解成线程的私有内存。

而堆内存中的对象对所有线程可见。堆内存中的对象可以被所有线程访问

##### 堆

堆内存用来存储Java中的对象。无论是成员变量，局部变量，还是类变量，它们指向的对象都存储在堆内存中

##### 比较

栈的内存要远远小于堆内存，如果你使用递归的话，那么你的栈很快就会充满。如果递归没有及时跳出，很可能发生StackOverFlowError问题。

##### 实验

启动一个应用分析

```
VM 参数: 
-agentlib:jdwp=transport=dt_socket,address=127.0.0.1:16930,suspend=y,server=n -XX:TieredStopAtLevel=1 -Xverify:none -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true -javaagent:C:\Users\李克国\AppData\Local\JetBrains\IntelliJIdea2020.1\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 
```

查看jvm 内存情况

```json
jmap -heap 测试

Heap Configuration:
    MinHeapFreeRatio         = 0       // 最小空闲堆 默认 40%
    MaxHeapFreeRatio         = 100    // 最小空闲堆 默认 70%
    MaxHeapSize              = 4265607168 (4068.0MB) // 最大堆空间
    NewSize                  = 89128960 (85.0MB) // 新生代堆大小 全部堆的1/3
    MaxNewSize               = 1421869056 (1356.0MB) // JVM允许新生代堆的大小
    OldSize                  = 179306496 (171.0MB) // 老年代堆大小
    NewRatio                 = 2 // 新生代与老年代比值 1:2
    SurvivorRatio            = 8 // 存活区域Eden区比值 S0:S1:Eden = 1:1:8
    MetaspaceSize            = 21807104 (20.796875MB) // 元数据区大小
    CompressedClassSpaceSize = 1073741824 (1024.0MB) // 压缩类空间大小
    MaxMetaspaceSize         = 17592186044415 MB // 最大元数据区大小
    G1HeapRegionSize         = 0 (0.0MB) // 使用G1时每个分区会形成多个Region，此为设置每个区大小

Heap Usage:
PS Young Generation
Eden Space:
    capacity = 683671552 (652.0MB) // 容量
    used     = 31011664 (29.575027465820312MB) // 使用
    free     = 652659888 (622.4249725341797MB) // 剩余
4.536047157334404% used
From Space:
    capacity = 3145728 (3.0MB)
    used     = 3085288 (2.9423599243164062MB)
    free     = 60440 (0.05764007568359375MB)
98.0786641438802% used
To Space:
    capacity = 31981568 (30.5MB)
    used     = 0 (0.0MB)
    free     = 31981568 (30.5MB)
0.0% used
PS Old Generation
    capacity = 207618048 (198.0MB)
    used     = 94956096 (90.55718994140625MB)
    free     = 112661952 (107.44281005859375MB)
45.73595451586174% used

43492 interned Strings occupying 4673224 bytes.
```

使用测压工具

```cmd
 sb -u 'http://localhost:15050/branch/settlement/participant/query/dict/line' -n 10000 -c 5 -m POST -v -h
```



```
Attaching to process ID 2232, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.112-b15

using thread-local object allocation.
Parallel GC with 4 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 0
   MaxHeapFreeRatio         = 100
   MaxHeapSize              = 4265607168 (4068.0MB)
   NewSize                  = 89128960 (85.0MB)
   MaxNewSize               = 1421869056 (1356.0MB)
   OldSize                  = 179306496 (171.0MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 320864256 (306.0MB)
   used     = 142642736 (136.0347137451172MB)
   free     = 178221520 (169.9652862548828MB)
   44.455788805593855% used
From Space:
   capacity = 1048576 (1.0MB)
   used     = 950272 (0.90625MB)
   free     = 98304 (0.09375MB)
   90.625% used
To Space:
   capacity = 14680064 (14.0MB)
   used     = 0 (0.0MB)
   free     = 14680064 (14.0MB)
   0.0% used
PS Old Generation
   capacity = 207618048 (198.0MB)
   used     = 98265552 (93.71333312988281MB)
   free     = 109352496 (104.28666687011719MB)
   47.32996622721354% used

43935 interned Strings occupying 4711880 bytes.

```

可以看到 ParallelGC下，young区大小为1024/3 = 341.3M，跟上述显示一致。CMS情况下则为332.75M，不是1/3，并且在xmx为2048M时，还是332.75M，这说明最大young区大小与Xmx参数无关。实际上，我的电脑上：64M * 并发GC线程数(4) * 13 / 10 =332.8M这个式子是jvm代码写死的，只跟GC线程数有关系。



CMS GC的 默认GC进程数是怎么来的？区分young区的parnew gc线程数和old区的cms线程数，分别为以下两参数：-XX:ParallelGCThreads=m-XX:ConcGCThreads=n  其中ParallelGCThreads 参数的默认值是：CPU核心数 <= 8，则为 ParallelGCThreads=CPU核心数，比如我的那个旧电脑是4CPU核心数 > 8，则为 ParallelGCThreads = CPU核心数 * 5/8 + 3 向下取整16核的情况下，ParallelGCThreads = 1332核的情况下，ParallelGCThreads = 2364核的情况下，ParallelGCThreads = 4372核的情况下，ParallelGCThreads = 48 ConcGCThreads的默认值则为：ConcGCThreads = (ParallelGCThreads + 3)/4 向下去整。ParallelGCThreads = 1~4时，ConcGCThreads = 1ParallelGCThreads = 5~8时，ConcGCThreads = 2ParallelGCThreads = 13~16时，ConcGCThreads = 4

## 其他记录

### XmnXmsXmxXss有什么区别

Xmn、Xms、Xmx、Xss都是JVM对内存的配置参数，我们可以根据不同需要区修改这些参数，以达到运行程序的最好效果。

-Xms 堆内存的最小大小，默认为物理内存的1/64

-Xmx 堆内存的最大大小，默认为物理内存的1/4

-Xmn 堆内新生代的大小。通过这个值也可以得到老生代的大小：-Xmx减去-Xmn

-Xss 设置每个线程可使用的内存大小，即栈的大小。在相同物理内存下，减小这个值能生成更多的线程，当然操作系统对一个进程内的线程数还是有限制的，不能无限生成。线程栈的大小是个双刃剑，如果设置过小，可能会出现**栈溢出**，特别是在该线程内有递归、大的循环时出现溢出的可能性更大，如果该值设置过大，就有影响到创建栈的数量，如果是多线程的应用，就会出现内存溢出的错误。

------

除了这些配置，JVM还有非常多的配置，常用的如下：

1. 堆设置
   - **-Xms**:初始堆大小
   - **-Xmx**:最大堆大小
   - **-Xmn**:新生代大小
   - **-XX:NewRatio**:设置新生代和老年代的比值。如：为3，表示年轻代与老年代比值为1：3
   - **-XX:SurvivorRatio**:新生代中Eden区与两个Survivor区的比值。注意Survivor区有两个。如：为3，表示Eden：Survivor=3：2，一个Survivor区占整个新生代的1/5 
   - **-XX:MaxTenuringThreshold**:设置转入老年代的存活次数。如果是0，则直接跳过新生代进入老年代
   - **-XX:PermSize**、**-XX:MaxPermSize**:分别设置永久代最小大小与最大大小（Java8以前）
   - **-XX:MetaspaceSize**、**-XX:MaxMetaspaceSize**:分别设置元空间最小大小与最大大小（Java8以后）
2. 收集器设置
   - **-XX:+UseSerialGC**:设置串行收集器
   - **-XX:+UseParallelGC**:设置并行收集器
   - **-XX:+UseParalledlOldGC**:设置并行老年代收集器
   - **-XX:+UseConcMarkSweepGC**:设置并发收集器
3. 垃圾回收统计信息
   - **-XX:+PrintGC**
   - **-XX:+PrintGCDetails**
   - **-XX:+PrintGCTimeStamps**
   - **-Xloggc:filename**
4. 并行收集器设置
   - **-XX:ParallelGCThreads=n**:设置并行收集器收集时使用的CPU数。并行收集线程数。
   - **-XX:MaxGCPauseMillis=n**:设置并行收集最大暂停时间
   - **-XX:GCTimeRatio=n**:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)
5. 并发收集器设置
   - **-XX:+CMSIncrementalMode**:设置为增量模式。适用于单CPU情况。
   - **-XX:ParallelGCThreads=n**:设置并发收集器新生代收集方式为并行收集时，使用的CPU数。并行收集线程数。