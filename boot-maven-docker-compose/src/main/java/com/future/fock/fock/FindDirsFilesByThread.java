package com.future.fock.fock;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * 异步用法
 *
 * 类说明：遍历指定目录（含子目录）找寻指定类型文件
 */
public class FindDirsFilesByThread implements Runnable{

	private File path;// 当前任务需要搜寻的目录


	private static final int THREAD_COUNT_NUM = 2;
	private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT_NUM);

	public FindDirsFilesByThread(File path) {
		this.path = path;
	}

	public static void main(String[] args) {
		try {
			long start=System.currentTimeMillis();
			FindDirsFilesByThread task = new FindDirsFilesByThread(new File("F:\\all-platform"));

			System.out.println("Task is Running......");
			Thread.sleep(1);
			int otherWork = 0;
			for (int i = 0; i < 100; i++) {
				otherWork = otherWork + i;
			}
			System.out.println("Main Thread done sth......,otherWork=" + otherWork);
			countDownLatch.countDown();
			new Thread(task).start();
			countDownLatch.await();
			System.out.println("Task end");
			System.out.println("use time:"+(System.currentTimeMillis()-start)+" ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		printFlie(path);
		countDownLatch.countDown();
	}
	
	public void printFlie(File f) {
		File[] files = f.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					printFlie(file);
				} else {
					// 遇到文件，检查
					if (file.getAbsolutePath().endsWith(".txt")) {
						System.out.println("文件：" + file.getAbsolutePath());
					}
				}
			}
		}
	}
}