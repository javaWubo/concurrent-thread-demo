package a8.ys8_3;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在代码中，虽然有30个线程在执行，但是只允许10个并发执行。Semaphore的构造方法
 Semaphore（int permits）接受一个整型的数字，表示可用的许可证数量。Semaphore（10）表示允
 许10个线程获取许可证，也就是最大并发数是10。Semaphore的用法也很简单，首先线程使用
 Semaphore的acquire()方法获取一个许可证，使用完之后调用release()方法归还许可证。还可以
 用tryAcquire()方法尝试获取许可证。
 */
public class SemaphoreTest {
        private static final int THREAD_COUNT = 88830;
        private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        private static Semaphore s = new Semaphore(10);
        private static AtomicInteger atomicInteger = new AtomicInteger();
        public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i< THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        s.acquire();
                        atomicInteger.incrementAndGet();
                        System.out.println(atomicInteger.get());
                        System.out.println("save data");
                        s.release();
                    } catch (InterruptedException e) {
                    }
                }
            });
        }
        Thread.sleep(100l);
            System.out.println("=============="+atomicInteger.get());
        threadPool.shutdown();
    }

}
