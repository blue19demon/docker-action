package com.future.jdk8.jms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Producter {

	public static void main(String[] args) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket=null;
		try {
			socket=new Socket("127.0.0.1", 9999);
			oos = new ObjectOutputStream(socket.getOutputStream());
			MessageRequest messageRequest = new MessageRequest();
			messageRequest.setMode(Mode.QUEUE);
			messageRequest.setName("test");
			messageRequest.setContent("test message");
			oos.writeObject(messageRequest);
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			Object receive = ois.readObject();
			System.out.println("receive:"+receive);
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
