####  题目

- 思考有多少种方式，在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程？

- 把多线程和并发相关知识带你梳理一遍，画一个脑图，截图上传到 Github 上。

### 总览

题目一：抽象一个返回执行结果的方法，模拟指定1s后返回结果，启动一个新线程池执行，阻塞主线程等待返回结果

```java
    interface Test {
        /**
         * 测试
         */
        void test() throws Exception;

        default int getResult() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }

        default void show(int result) {
            System.out.println(this.getClass().getName() + " result :" + result);
        }
    }
```

本次共有11中方式实现，

测试结果

```properties
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl1 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl2 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl3 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl4 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl5 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl6 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl7 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl8 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl9 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl10 result :1
cn.leaf.exercise.multithreading.WaitResultDemo$WaitResultImpl11 result :1
```

多线程脑图如下：

![](https://raw.githubusercontent.com/li-keguo/JAVA-01/main/Week_04/JAVA%E5%A4%9A%E7%BA%BF%E7%A8%8B.png)

多线程这块学的还不够理想，本周工作忙，作业完成不是很满意，后续继续完善

