package com.future.fock.fock;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/** 同步用法 */
public class SumArrayByForkJoinPool {
	private static class SumTask extends RecursiveTask<Long> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// 总批次数
		private static int BATCH_SIZE = 10;
		// 单个批次大小
		private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / BATCH_SIZE;
		private int[] src; // 表示我们要实际统计的数组
		private int fromIndex;// 开始统计的下标
		private int toIndex;// 统计到哪里结束的下标

		public SumTask(int[] src, int fromIndex, int toIndex) {
			this.src = src;
			this.fromIndex = fromIndex;
			this.toIndex = toIndex;
		}

		@Override
		protected Long compute() {
			if (toIndex - fromIndex < THRESHOLD) {
				return subTask(src, fromIndex, toIndex);
			} else {
				// fromIndex....mid....toIndex
				// 1...................70....100
				int mid = (fromIndex + toIndex) / 2;
				// 将任务一分为二
				SumTask left = new SumTask(src, fromIndex, mid);
				SumTask right = new SumTask(src, mid + 1, toIndex);
				invokeAll(left, right); // 提交任务
				return left.join() + right.join();
			}
		}

		public Long subTask(int[] src, int fromIndex, int toIndex) {
			Long count = 0L;
			for (int k = 0; k < 100; k++) {
				for (int i = fromIndex; i <= toIndex; i++) {
					// SleepTools.ms(1);
					count = count + src[i];
				}
			}
			return count;
		}
	}

	public static void main(String[] args) {

		ForkJoinPool pool = new ForkJoinPool();
		int[] src = MakeArray.makeArray();

		SumTask innerFind = new SumTask(src, 0, src.length - 1);

		long start = System.currentTimeMillis();

		Long a = pool.invoke(innerFind);// 同步调用
		System.out.println("Task is Running.....");
		System.out.println("The count is " + innerFind.join() + " spend time:" + (System.currentTimeMillis() - start)
				+ "ms,result=" + a);

	}
}

class MakeArray {

	public static final int ARRAY_LENGTH = 10000001;

	public static int[] makeArray() {
		int[] arr = new int[ARRAY_LENGTH];
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			arr[i] = i + 1;
		}
		return arr;
	}
}