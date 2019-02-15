package a5.ys5_20;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionUseCase {
    Lock lock = new ReentrantLock();
//    获取一个Condition必须通过Lock的newCondition()方法
//      condition 一般出现在成员变量
    Condition condition = lock.newCondition();
    public void conditionWait() throws InterruptedException {
        lock.lock();
        try{
            condition.await();
        }finally {
            lock.unlock();
        }
    }
    public void conditionSignal() throws InterruptedException {
        lock.lock();
        try{
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

}
