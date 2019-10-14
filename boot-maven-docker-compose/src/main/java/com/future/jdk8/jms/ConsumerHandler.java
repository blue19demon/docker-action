package com.future.jdk8.jms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ConsumerHandler implements Runnable {
	private Socket socket;

	public ConsumerHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Object messageRequest = ois.readObject();
			System.out.println("i receive ="+messageRequest);
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
}