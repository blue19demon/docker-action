package com.future.fock.fock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 异步用法
 *
 * 类说明：遍历指定目录（含子目录）找寻指定类型文件
 */
public class FindDirsFilesByForkJoinPool extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File path;// 当前任务需要搜寻的目录

	public FindDirsFilesByForkJoinPool(File path) {
		this.path = path;
	}

	public static void main(String[] args) {
		try {
			long start=System.currentTimeMillis();
			// 用一个 ForkJoinPool 实例调度总任务
			ForkJoinPool pool = new ForkJoinPool();
			FindDirsFilesByForkJoinPool task = new FindDirsFilesByForkJoinPool(new File("F:\\all-platform"));

			pool.execute(task);// 异步调用

			System.out.println("Task is Running......");
			Thread.sleep(1);
			int otherWork = 0;
			for (int i = 0; i < 100; i++) {
				otherWork = otherWork + i;
			}
			System.out.println("Main Thread done sth......,otherWork=" + otherWork);
			task.join();// 阻塞的方法
			System.out.println("Task end");
			System.out.println("use time:"+(System.currentTimeMillis()-start)+" ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void compute() {

		List<FindDirsFilesByForkJoinPool> subTasks = new ArrayList<>();

		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					subTasks.add(new FindDirsFilesByForkJoinPool(file));
				} else {
					// 遇到文件，检查
					if (file.getAbsolutePath().endsWith(".txt")) {
						System.out.println("文件：" + file.getAbsolutePath());
					}
				}
			}
			if (!subTasks.isEmpty()) {
				for (FindDirsFilesByForkJoinPool subTask : invokeAll(subTasks)) {
					subTask.join();// 等待子任务执行完成
				}
			}
		}
	}
}