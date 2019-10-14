package com.future.fock.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	private static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
	public static void main(String[] args) {
		final CountDownLatch start = new CountDownLatch(1);	//用一个信号控制一组线程的开始，初始化为1
		final CountDownLatch end = new CountDownLatch(10);	//要等待N个线程的结束，初始化为N，这里是10
		Runnable r = new Runnable(){
			public void run(){
				try {
					start.await();	//阻塞，这样start.countDown()到0，所有阻塞在start.await()处的线程一起执行
					Thread.sleep((long) (Math.random()*10000));
					System.out.println(getNow()+"--"+Thread.currentThread().getName()+"--执行完成");
					end.countDown();//非阻塞，每个线程执行完，让end--,这样10个线程执行完end倒数到0，主线程的end.await()就可以继续执行
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		for(int i=0;i<10;i++){
			new Thread(r).start();	//虽然开始了10个线程，但所有线程都阻塞在start.await()处
		}
		System.out.println(getNow()+"--线程全部启动完毕，休眠3s再让10个线程一起执行");
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(getNow()+"--开始");
		start.countDown();	//start初始值为1，countDown()变成0，触发10个线程一起执行
		try {
			end.await();		//阻塞，等10个线程都执行完了才继续往下。
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(getNow()+"--10个线程都执行完了，主线程继续往下执行！");
	}
	private static String getNow(){
		return sdf.format(new Date());
	}
}