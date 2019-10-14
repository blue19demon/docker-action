package com.future.jdk8.mq.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.future.jdk8.mq.domain.CommunicationWay;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.listener.MessageListener;
import com.future.jdk8.mq.server.JMSMessage;

public class JMSConsumerHandler<T> implements Runnable {

	private CommunicationWay communicationWay;

	private Socket socket;

	private MessageListener<T> messageListener;

	public JMSConsumerHandler(CommunicationWay communicationWay, Socket socket, MessageListener<T> messageListener) {
		super();
		this.communicationWay = communicationWay;
		this.socket = socket;
		this.messageListener = messageListener;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			Object msg = ois.readObject();
			if (msg instanceof JMSMessage) {
				JMSMessage jmsMessage = (JMSMessage) msg;
				if(communicationWay.name().equals(jmsMessage.getName())) {
             	   Message<T> message=new Message<T>();
             	   message.setContext((T)jmsMessage.getContent());
             	   message.setAutoAcknowledge(jmsMessage.getAutoAcknowledge());
             	   message.setDeliverMode(jmsMessage.getDeliverMode());
             	   message.setMsgId(jmsMessage.getMsgId());
             	   message.setTimestamp(jmsMessage.getTimestamp());
             	   messageListener.onMessage(message);
                }
			} else {
				System.out.println("i receive =" + msg);
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
}
