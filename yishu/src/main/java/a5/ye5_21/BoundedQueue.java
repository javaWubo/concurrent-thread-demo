package a5.ye5_21;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界队列
 * 当队列空：队列的获取操作阻塞获取线程，直到队列中有了新的元素
 * 当队列满：队列的插入操作阻塞插入线程操作，直到队列中出现“空位”
 * --------------------------
 *
 */
public class BoundedQueue<T> {
    private Object[] items ;

    private int addIndex,removeIndex,count;

    private Lock lock = new ReentrantLock();

    Condition notFull = lock.newCondition();

    Condition notEmpty = lock.newCondition();

    public BoundedQueue(int size) {
        this.items = new Object[size];
    }

    private void add(T t) throws InterruptedException {
        lock.lock();
        try{
            System.out.println("add begin : "+JSONObject.toJSONString(items));
            /**
             * TODO
             * 在添加和删除方法中使用while循环而非if判断，目的是防止过早或意外的通知，
             * 只有条件符合才能够退出循环。回想之前提到的等待/通知的经典范式，二者是非常类似的。
             */
            while(count==items.length){
                System.out.println("add await");
                //TODO await() 会释放锁
                notFull.await();
            }

            items[addIndex] = t;
            if(++addIndex==items.length){
                addIndex = items.length-1 ;
            }
            ++count;
            System.out.println("add end : "+JSONObject.toJSONString(items));
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    private T remove() throws InterruptedException {
        lock.lock();
        System.out.println("remove begin : "+JSONObject.toJSONString(items));
        try{
            while (count == 0){

                notEmpty.await();
            }
            Object x = items[removeIndex];
            if(++removeIndex == items.length){
                removeIndex = 0;
            }

            --count;
            System.out.println("remove end : "+JSONObject.toJSONString(items));
            notFull.signal();
            return (T)x;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final BoundedQueue<String> boundedQueue = new BoundedQueue<>(5);
        try {
            boundedQueue.add("z1");
            boundedQueue.add("z2");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        Thread.sleep(3000l);
                        System.out.println("休眠 ms :{"+(System.currentTimeMillis()-start)+"}");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        System.out.println(boundedQueue.remove());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"Thread-tt1");
            thread.start();
            boundedQueue.add("z3");
            boundedQueue.add("z4");
            boundedQueue.add("z5");
            boundedQueue.add("z6");
            boundedQueue.add("z7");
            boundedQueue.add("z8");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
