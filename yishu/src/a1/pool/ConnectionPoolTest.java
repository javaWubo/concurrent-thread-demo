package a1.pool;

import a1.pool.impl.ConnectionPoolImpl;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发编程的艺术：4.4.2
 * 案例巧妙的使用 了CountDownLatch 来 控制 所有线程去同时启动获取 数据库链接
 * 利用了  AtomicInteger 线程对同一个计数器进行原子加的方式进行获取成功 和获取失败的总数统计
 */
public class ConnectionPoolTest {
    static ConnectionPoolImpl connectionPool = new ConnectionPoolImpl(10);
    //保证所有的connectionRunner 能同时开始
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end = null;

    public static void main(String[] args) {
        //todo wb  线程数量()
        int threadNum = 50;
        end = new CountDownLatch(threadNum);
        //获取链接数
        int count = 20;
        AtomicInteger go = new AtomicInteger();
        AtomicInteger notGo = new AtomicInteger();

        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(new ConnectionRunner(count,go,notGo),"wb-run-t");
            thread.start();
        }

        start.countDown();
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总获取次数{"+threadNum*count+"}");
        System.out.println("成功获取{"+go+"}");
        System.out.println("失败获取{"+notGo+"}");
    }

    static class ConnectionRunner implements Runnable{
        int count ;
        AtomicInteger go ;
        AtomicInteger notGo ;

        public ConnectionRunner(int count, AtomicInteger go, AtomicInteger notGo) {
            this.count = count;
            this.go = go;
            this.notGo = notGo;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count>0){
                try{

                    Connection connection = connectionPool.fetchConnection(1000);
                    if(connection !=null){
                        try{
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            connectionPool.releseConnection(connection);
                            go.incrementAndGet();
                        }
                    }else{
                        notGo.incrementAndGet();
                    }

                }catch (Exception e){


                }finally {
                    count --;
                }
            }
            //bugfix
            end.countDown();
        }
    }
}
