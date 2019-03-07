package a1.pool.impl;

import a1.pool.ConnectionDriver;
import a1.pool.ConnectionPool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPoolImpl implements ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPoolImpl(int initSize) {
        if(initSize<0){
            throw new  RuntimeException("是不是有病！");
        }
        for (int i = 0; i < initSize; i++) {
            pool.add(ConnectionDriver.createConnection());
        }
    }

    @Override
    public void releseConnection(Connection connection) {

        if(connection != null){
            synchronized (pool){
                //归还链接
                pool.addLast(connection);
                //唤起所有等待线程
                pool.notifyAll();
            }
        }

    }

    @Override
    public Connection fetchConnection(long mills) throws Exception {
        synchronized (pool){
            if(mills<0){
                if(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long futureTime = System.currentTimeMillis() + mills;
                long waitTime = mills;
                while (pool.isEmpty() && waitTime >0){
                    pool.wait(waitTime);
                    waitTime = futureTime - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
