package com.future.fock.fock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/** 传统用法 */
public class SumArrayByCallable {

	// 总批次数
	private  static int BATCH_SIZE = 10;
	// 单个批次大小
	private  static int THRESHOLD = MakeArray.ARRAY_LENGTH / BATCH_SIZE;

	static class MakeArray {

		public static final int ARRAY_LENGTH = 10000001;

		public static int[] makeArray() {
			int[] arr = new int[ARRAY_LENGTH];
			for (int i = 0; i < ARRAY_LENGTH; i++) {
				arr[i] = i + 1;
			}
			return arr;
		}
	}

	public static void main(String[] args) {

		try {
			int[] src = MakeArray.makeArray();
			// 用于收集任务
			List<FutureTask<Long>> futureTasks = new ArrayList<>();
			// 创建线程池
			ExecutorService pool =null;
			if(THRESHOLD<=0){
				// 创建线程池
				pool = Executors.newFixedThreadPool(1);
				SumTask innerFind = new SumTask(src, 0, src.length-1);
				FutureTask<Long> futureTask = new FutureTask<Long>(innerFind);
				futureTasks.add(futureTask);
				// 提交任务到线程池
				pool.submit(futureTask);
			}else {
				// 创建线程池
				pool= Executors.newFixedThreadPool(BATCH_SIZE);
				for (int i = 0; i < BATCH_SIZE; i++) {
					SumTask innerFind = buildSumTask(src, i);
					FutureTask<Long> futureTask = new FutureTask<Long>(innerFind);
					futureTasks.add(futureTask);
					// 提交任务到线程池
					pool.submit(futureTask);
				}
			}
			

			long start = System.currentTimeMillis();

			Long result = 0L;
			for (FutureTask<Long> task : futureTasks) {
				try {
					// FutureTask的get()方法会自动阻塞，知道得到任务执行结果为止
					result += task.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Task is Running.....");
			pool.shutdown();
			System.out.println("The count is " + result + " spend time:" + (System.currentTimeMillis() - start) + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static SumTask buildSumTask(int[] src, int i) {
		// 整除
		int mod = MakeArray.ARRAY_LENGTH % BATCH_SIZE;
		if (mod == 0) {
			return new SumTask(src, THRESHOLD * i, THRESHOLD * (i + 1) - 1);
		} else {
			int fromIndex = i * (THRESHOLD + 1);// 开始统计的下标
			int toIndex = (i + 1) * (THRESHOLD + 1);// 统计到哪里结束的下标
			Boolean isLast = (i == (BATCH_SIZE - 1));
			// 最后一批次
			if (isLast) {
				toIndex = MakeArray.ARRAY_LENGTH;
			}
			toIndex = toIndex - 1;
			return new SumTask(src, fromIndex, toIndex);
		}
	}

}

class SumTask implements Callable<Long> {
	private int[] src; // 表示我们要实际统计的数组
	private int fromIndex;// 开始统计的下标
	private int toIndex;// 统计到哪里结束的下标

	public SumTask(int[] src, int fromIndex, int toIndex) {
		this.src = src;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public Long call() throws Exception {
		Long res = 0L;
		for (int k = 0; k < 100; k++) {
			for (int i = fromIndex; i <= toIndex; i++) {
				res += src[i];
			}
		}
		return res;
	}
}
