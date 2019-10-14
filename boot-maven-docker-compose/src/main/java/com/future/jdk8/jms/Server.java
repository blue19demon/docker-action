package com.future.jdk8.jms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static  List<ClientInfo> clients = new ArrayList<ClientInfo>();
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	public static void main(String[] args) {
		start(9999);
	}

	private static void start(int port) {
		ServerSocket ss = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ss = new ServerSocket(port);
			System.out.println("server started at 【"+address.getHostAddress()+"】 in 【"+port+"】");
			while (true) {
				Socket socket = ss.accept();
				executorService.execute(new ServerHandler(socket,clients));
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
}

class ServerHandler implements Runnable {
	private Socket socket;
	private  List<ClientInfo> clients;
	public ServerHandler(Socket socket,List<ClientInfo> clients) {
		super();
		this.socket = socket;
		this.clients = clients;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Object receiver = ois.readObject();
		    if(receiver instanceof ClientInfo) {
		    	callbackClientInfo((ClientInfo)receiver);
		    }else if(receiver instanceof MessageRequest) {
		    	callbackMessage((MessageRequest)receiver);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void callbackClientInfo(ClientInfo receiver) {
		try {
			System.out.println("ClientInfo="+receiver);
			clients.add(receiver);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("regist OK");
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void callbackMessage(MessageRequest messageRequest) throws IOException {
		System.out.println("messageRequest="+messageRequest);
		Object result = handle(messageRequest);
		System.out.println("result="+result);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(result);
		oos.flush();
		oos.close();
		for (ClientInfo clientInfo : clients) {
			callback(clientInfo, messageRequest);
		}
	}
	
	public static void callback(ClientInfo client,MessageRequest messageRequest) {
		ObjectOutputStream oos = null;
		Socket socket=null;
		try {
			socket=new Socket(client.getIp(), client.getPort());
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(messageRequest.getContent());
			oos.flush();
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

	private Object handle(MessageRequest messageRequest) {
		return "send OK";
	}

}
