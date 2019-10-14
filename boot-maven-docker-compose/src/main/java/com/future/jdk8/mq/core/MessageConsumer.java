package com.future.jdk8.mq.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.future.jdk8.mq.domain.CommunicationWay;
import com.future.jdk8.mq.handler.JMSConsumerHandler;
import com.future.jdk8.mq.listener.MessageListener;

public class MessageConsumer {
	
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	private CommunicationWay communicationWay;

	private ServerSocket serverSocket;

	public MessageConsumer(CommunicationWay communicationWay, ServerSocket serverSocket) {
		super();
		this.communicationWay = communicationWay;
		this.serverSocket = serverSocket;
	}
	public void close() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setMessageListener(MessageListener<?> messageListener) {
		try {
			Socket socket = serverSocket.accept();
			executorService.execute(new JMSConsumerHandler(communicationWay,socket,messageListener));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
