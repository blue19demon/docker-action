package com.future.jdk8.mq.test.topic;

import java.io.IOException;

import com.future.jdk8.mq.core.Connection;
import com.future.jdk8.mq.core.ConnectionFactory;
import com.future.jdk8.mq.core.MessageConsumer;
import com.future.jdk8.mq.core.Session;
import com.future.jdk8.mq.core.SocketMQConnectionFactory;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.domain.Topic;
import com.future.jdk8.mq.listener.MessageListener;

public class MQConsumerTopic2 {
	public static void main(String[] args) throws IOException {
        ConnectionFactory connectionFactory = new SocketMQConnectionFactory("tcp://169.254.201.224:61616");
        Connection connection = connectionFactory.createConnection(7766);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test-topic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener<String>() {
            @Override
            public void onMessage(Message<String> message) {
            	System.out.println("收到消息:"+ message.getContext());
            }
        });
     	System.in.read();
        consumer.close();
        session.close();
        connection.close();
	}
}
