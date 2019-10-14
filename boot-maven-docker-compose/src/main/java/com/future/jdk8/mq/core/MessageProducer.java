package com.future.jdk8.mq.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.future.jdk8.mq.domain.CommunicationWay;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.exception.JMSException;
import com.future.jdk8.mq.server.JMSMessage;

public class MessageProducer {
	
	private Session session;
	
	private CommunicationWay communicationWay;
	
	private Socket socket=null;
	public MessageProducer(Session session, CommunicationWay communicationWay) {
		super();
		this.session = session;
		this.communicationWay = communicationWay;
	}

	public void send(Message<?> message) {
		ObjectOutputStream oos = null;
		try {
			ServerAddress serverAddress = session.getServerAddress();
			socket=new Socket(serverAddress.getIp(), serverAddress.getPort());
			oos = new ObjectOutputStream(socket.getOutputStream());
			JMSMessage jmsMessage = new JMSMessage();
			jmsMessage.setDeliverMode(session.getDeliverMode());
			jmsMessage.setAutoAcknowledge(session.getAutoAcknowledge());
			jmsMessage.setName(communicationWay.name());
			jmsMessage.setContent(message.getContext());
			oos.writeObject(jmsMessage);
			oos.flush();
		} catch (Exception e) {
			throw new JMSException(402,e.getMessage());
		}
	}

	public void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
