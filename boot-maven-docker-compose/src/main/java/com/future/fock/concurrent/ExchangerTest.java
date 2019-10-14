package com.future.fock.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;

public class ExchangerTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
	private static Exchanger<Queue<Integer>> changer = new Exchanger<Queue<Integer>>();
 
	public static void main(String[] args) {
		new Thread(new ExchangerTest().new ProducerLoop()).start();
		new Thread(new ExchangerTest().new ConsumerLoop()).start();
	}
	class ProducerLoop implements Runnable{
		private Queue<Integer> pBuffer = new LinkedBlockingQueue<Integer>();
		private final int maxnum = 10;
 
		@Override
		public void run() {
			try{
				for(;;){
					Thread.sleep(500);
					pBuffer.offer((int) Math.round(Math.random()*100));
					if(pBuffer.size() == maxnum){
						System.out.println(getNow()+"--producer交换前");
						pBuffer = changer.exchange(pBuffer);
						System.out.println(getNow()+"--producer交换后");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}	
	class ConsumerLoop implements Runnable{
		private Queue<Integer> cBuffer = new LinkedBlockingQueue<Integer>();
		
		@Override
		public void run() {
			try{
				for(;;){
					if(cBuffer.size() == 0){
						System.out.println("\n"+getNow()+"--consumer交换前");
						cBuffer = changer.exchange(cBuffer);
						System.out.println(getNow()+"--consumer交换后");
					}
					System.out.print(cBuffer.poll()+" ");
					Thread.sleep(500);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}	
	private String getNow(){
		return sdf.format(new Date());
	}
}