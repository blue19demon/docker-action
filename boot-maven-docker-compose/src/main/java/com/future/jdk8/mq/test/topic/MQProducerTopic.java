package com.future.jdk8.mq.test.topic;

import com.future.jdk8.mq.core.Connection;
import com.future.jdk8.mq.core.ConnectionFactory;
import com.future.jdk8.mq.core.MessageProducer;
import com.future.jdk8.mq.core.Session;
import com.future.jdk8.mq.core.SocketMQConnectionFactory;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.domain.Topic;
public class MQProducerTopic {

	public static void main(String[] args) {
        ConnectionFactory connectionFactory = new SocketMQConnectionFactory("tcp://169.254.201.224:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	Topic topic = session.createTopic("test-topic");
        MessageProducer producer = session.createProducer(topic);
        Message<?> textMessage = session.createMessage("我是topic");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
	}
}
