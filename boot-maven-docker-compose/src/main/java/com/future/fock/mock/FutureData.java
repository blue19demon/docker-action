package com.future.fock.mock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Future数据，构造很快，但是是一个虚拟的数据，需要装配RealData
 */
public class FutureData implements Data {

    private RealData realData = null;
    private boolean isReady = false;

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    @Override
    public String getResult() {
        while (!isReady) {
            try {
                lock.lock();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return realData.getResult();
    }

    public void setRealData(RealData realData) {
        lock.lock();
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        condition.signal();
        lock.unlock();
    }
}