package com.future.jdk8.mq.server;

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

public class JMSServer {
	private static  List<JMSClientConnMessage> jmsClientConnLists = new ArrayList<JMSClientConnMessage>();
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	public static void main(String[] args) {
		start(61616);
	}

	private static void start(int port) {
		ServerSocket ss = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ss = new ServerSocket(port);
			System.out.println("server started at 【"+address.getHostAddress()+"】 in 【"+port+"】");
			while (true) {
				Socket socket = ss.accept();
				executorService.execute(new ServerHandler(socket,jmsClientConnLists));
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
	private List<JMSClientConnMessage> jmsClientConnLists;
	public ServerHandler(Socket socket,List<JMSClientConnMessage> jmsClientConnLists) {
		super();
		this.socket = socket;
		this.jmsClientConnLists = jmsClientConnLists;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Object receiver = ois.readObject();
		    if(receiver instanceof JMSClientConnMessage) {
		    	callbackJMSClientConnectionMessage((JMSClientConnMessage)receiver);
		    }else if(receiver instanceof JMSMessage) {
		    	callbackJSMMessage((JMSMessage)receiver);
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

	private void callbackJMSClientConnectionMessage(JMSClientConnMessage receiver) {
		try {
			System.out.println("ClientConnectionMessage="+receiver);
			if(!jmsClientConnLists.contains(receiver)) {
				jmsClientConnLists.add(receiver);
			}
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("regist OK");
			oos.flush();
			oos.close();
			System.out.println("regist OK");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void callbackJSMMessage(JMSMessage jmsMessage) throws IOException {
		System.out.println("JMSMessage="+jmsMessage);
		if(jmsMessage.getDeliverMode()==DeliverMode.QUEUE) {
			//queue
			callback(jmsClientConnLists.get(0), jmsMessage);
		}else {
			//topic
			if(jmsMessage.getDeliverMode()==DeliverMode.TOPIC) {
				for (JMSClientConnMessage clientConnectionMessage : jmsClientConnLists) {
					callback(clientConnectionMessage, jmsMessage);
				}
			}
		}
	}
	
	public static void callback(JMSClientConnMessage client,JMSMessage jmsMessage) {
		ObjectOutputStream oos = null;
		Socket socket=null;
		try {
			socket=new Socket(client.getIp(), client.getPort());
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(jmsMessage);
			oos.flush();
			System.out.println("send OK");
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
