package a1.pool2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimplePool {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock =  reentrantReadWriteLock.writeLock();
        writeLock.lock();

    }
}
