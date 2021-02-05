package cn.leaf.exercise.multithreading;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 等待执行结果
 *
 * @author 李克国
 * @version 1.0.0
 * @date 2021/1/27 22:37
 */
public class WaitResultDemo {

    public static void main(String[] args) throws Exception {
        new WaitResultImpl1().test();
        new WaitResultImpl2().test();
        new WaitResultImpl3().test();
        new WaitResultImpl4().test();
        new WaitResultImpl5().test();
        new WaitResultImpl6().test();
        new WaitResultImpl7().test();
        new WaitResultImpl8().test();
        new WaitResultImpl9().test();
        new WaitResultImpl10().test();
        new WaitResultImpl11().test();
    }

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


    static class WaitResultImpl1 implements Test {
        private volatile int[] result = {0};

        @Override
        public void test() {

            new Thread(() -> result[0] = getResult()).start();
            while (true) {
//                System.out.println(result[0]);
                if (result[0] != 0) {
                    break;
                }
            }
            show(result[0]);
        }
    }

    static class WaitResultImpl2 implements Test {

        @Override
        public void test() throws InterruptedException {
            ResultBean resultBean = new ResultBean();
            new Thread(resultBean::set).start();
            show(resultBean.get());
        }

        class ResultBean {
            final ReentrantLock lock = new ReentrantLock();
            final AtomicReference<Integer> result = new AtomicReference<>(0);
            final Condition condition = lock.newCondition();
            public  void set() {
                lock.lock();
                try {
                    result.set(getResult());
                    condition.signal();
                } finally {
                    lock.unlock();

                }
            }

            public Integer get() throws InterruptedException {
                Integer i;
                lock.lock();
                try {
                    condition.await();
                    i= result.get();
                } finally {
                    lock.unlock();
                }
                return i;
            }
        }
    }

    static class WaitResultImpl3 implements Test {

        @Override
        public void test() throws Exception {
            int i = ((Callable<Integer>) this::getResult).call();
            show(i);
        }
    }

    static class WaitResultImpl4 implements Test {

        @Override
        public void test() throws Exception {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            Future<Integer> future = executorService.submit(this::getResult);
            show(future.get());
            executorService.shutdown();
        }
    }

    static class WaitResultImpl5 implements Test {

        @Override
        public void test() throws Exception {
            CountDownLatch endLatch = new CountDownLatch(1);
            AtomicInteger i = new AtomicInteger();
            new Thread(() -> {
                i.set(getResult());
                endLatch.countDown();
            }).start();
            endLatch.await();
            show(i.get());
        }
    }

    static class WaitResultImpl6 implements Test {

        @Override
        public void test() throws Exception {
            Integer join = CompletableFuture.supplyAsync(this::getResult).join();
            show(join);
        }
    }

    static class WaitResultImpl7 implements Test {

        @Override
        public void test() throws Exception {
            FutureTask<Integer> task = new FutureTask<Integer>(this::getResult);
            new Thread(task).start();
            show(task.get());

        }
    }

    static class WaitResultImpl8 implements Test {

        @Override
        public void test() throws Exception {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            FutureTask<Integer> task = new FutureTask<>(this::getResult);
            executor.submit(task);
            show(task.get());
            executor.shutdown();
        }
    }

    static class WaitResultImpl9 implements Test {

        @Override
        public void test() throws Exception {
            AtomicReference<Integer> result = new AtomicReference<>(0);
            Thread thread = new Thread(() -> {
                result.set(getResult());
            });
            thread.start();
            thread.join();
            show(result.get());
        }
    }

    static class WaitResultImpl10 implements Test {

        @Override
        public void test() throws Exception {
            ExecutorService executorService = Executors.newCachedThreadPool();
            CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
            completionService.submit(this::getResult);
            show(completionService.take().get());
            executorService.shutdown();
        }

    }

    static class WaitResultImpl11 implements Test {

        @Override
        public void test() throws Exception {
            Semaphore semaphore = new Semaphore(0);
            AtomicReference<Integer> result = new AtomicReference<>(0);
            Thread thread = new Thread(() -> {
                result.set(getResult());
                semaphore.release();

            });
            thread.start();
            semaphore.acquire();
            show(result.get());
        }

    }
}
