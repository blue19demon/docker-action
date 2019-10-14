package com.future.fock.my.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyFutureMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyFutureTask<String> futureTask = new MyFutureTask<String>(new OCallable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(6000);
				return "hello";
			}
		});

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(futureTask);

        System.out.println("请求完毕！");

        try {
            Thread.sleep(2000);
            System.out.println("这里经过了一个2秒的操作！");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("真实数据：" + futureTask.get());
        executorService.shutdown();
    }
}