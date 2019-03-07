package a1;

import java.util.concurrent.TimeUnit;

/**
 * 异常对释放锁的影响
 * 结论 ： 当一个线程抛异常后会将锁释放掉 不会影响之后需要这个锁的线程
 *
 */
public class ExceptionThread {
   static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new T1Run(),"wb-t1=====");
        t1.start();

        TimeUnit.SECONDS.sleep(10);

       Thread t2 =  new Thread(new T2Run(),"wb-t2--=-=-=-=");

       t2.start();
    }

    static class T1Run implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                int a =  1/0;
            }
        }
    }
    static class T2Run implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() +"enter monitor");
            }
        }
    }

}
