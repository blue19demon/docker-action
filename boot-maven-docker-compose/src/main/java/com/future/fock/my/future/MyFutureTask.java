package com.future.fock.my.future;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyFutureTask<T> implements Runnable{

	private OCallable<T> oCallable = null;
	private boolean isReady = false;
	private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

	public MyFutureTask(MyCallable<T> myCallable) {
		this.oCallable = (OCallable<T>) myCallable;
	}

	@Override
	public void run() {
		try {
			T r=oCallable.call();
			oCallable.setResult(r);
			set(oCallable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public T get() {
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
        return oCallable.getResult();
	}
	
	 public void set(OCallable<T> oCallable) {
	        lock.lock();
	        if (isReady) {
	            return;
	        }
	        this.oCallable = oCallable;
	        isReady = true;
	        condition.signal();
	        lock.unlock();
	    }
}
