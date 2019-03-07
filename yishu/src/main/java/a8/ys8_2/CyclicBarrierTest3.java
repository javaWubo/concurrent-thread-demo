package a8.ys8_2;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier::isBroken() 用来判断 被阻塞的线程是否被中断
 */
public class CyclicBarrierTest3 {
    static CyclicBarrier c = new CyclicBarrier(3);
    public static void main(String[] args) throws InterruptedException,
    BrokenBarrierException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
            }
        });
        thread.start();
        thread1.start();
        thread1.interrupt();
        try {
            c.await();
        } catch (Exception e) {
            System.out.println(c.isBroken());
        }

    }
}