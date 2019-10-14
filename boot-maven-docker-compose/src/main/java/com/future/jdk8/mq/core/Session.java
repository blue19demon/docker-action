package com.future.jdk8.mq.core;

import java.io.IOException;
import java.net.ServerSocket;

import com.future.jdk8.mq.domain.CommunicationWay;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.domain.Queue;
import com.future.jdk8.mq.domain.Topic;
import com.future.jdk8.mq.server.AcknowledgeMode;
import com.future.jdk8.mq.server.DeliverMode;

import lombok.Data;
@Data
public class Session{

	public static final AcknowledgeMode AUTO_ACKNOWLEDGE = AcknowledgeMode.AUTO_ACKNOWLEDGE;
	public static final AcknowledgeMode MANUAL_ACKNOWLEDGE = AcknowledgeMode.MANUAL_ACKNOWLEDGE;
	
	private ServerAddress serverAddress;
	
	private ServerSocket serverSocket;
	
	private boolean isPersistent;
	
	private AcknowledgeMode autoAcknowledge;

	private DeliverMode deliverMode;
	

	public Queue createQueue(String queueName) {
		this.deliverMode=DeliverMode.QUEUE;
		return new Queue(queueName);
	}

	public MessageProducer createProducer(CommunicationWay communicationWay) {
		return new MessageProducer(this,communicationWay);
	}

	public Message<Object> createMessage(Object context) {
		Message<Object> textMessage=new Message<Object>();
		textMessage.setDeliverMode(deliverMode);
		textMessage.setContext(context);
		textMessage.setAutoAcknowledge(autoAcknowledge);
		return textMessage;
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

	public MessageConsumer createConsumer(CommunicationWay communicationWay) {
		return new MessageConsumer(communicationWay,serverSocket);
	}
	public Session(ServerAddress serverAddress, boolean isPersistent,
			AcknowledgeMode autoAcknowledge) {
		super();
		this.serverAddress = serverAddress;
		this.isPersistent = isPersistent;
		this.autoAcknowledge = autoAcknowledge;
	}
	public Session(ServerAddress serverAddress, ServerSocket serverSocket, boolean isPersistent,
			AcknowledgeMode autoAcknowledge) {
		super();
		this.serverAddress = serverAddress;
		this.serverSocket = serverSocket;
		this.isPersistent = isPersistent;
		this.autoAcknowledge = autoAcknowledge;
	}

	public Topic createTopic(String topicName) {
		this.deliverMode=DeliverMode.TOPIC;
		return new Topic(topicName);
	}

}
