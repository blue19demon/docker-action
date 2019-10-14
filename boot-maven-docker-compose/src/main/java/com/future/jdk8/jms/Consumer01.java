package com.future.jdk8.jms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 监听者
 * @author Administrator
 *
 */
public class Consumer01 {
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	public static void main(String[] args) {
		start(7777);
	}
	private static void start(int port) {
		ServerSocket ss = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ss = new ServerSocket(port);
			String hostAddress = address.getHostAddress();
			System.out.println("server started at 【"+hostAddress+"】 in 【"+port+"】");
			regist(hostAddress,port);
			while (true) {
				Socket socket = ss.accept();
				executorService.execute(new ConsumerHandler(socket));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static void regist(String hostAddress,int port) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket=null;
		try {
			socket=new Socket(hostAddress, 9999);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ClientInfo clientInfo = new ClientInfo();
			clientInfo.setIp(hostAddress);
			clientInfo.setPort(port);
			oos.writeObject(clientInfo);
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			Object receive = ois.readObject();
			System.out.println("regist receive:"+receive);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(socket!=null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

