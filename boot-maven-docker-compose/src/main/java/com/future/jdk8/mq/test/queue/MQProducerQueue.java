package com.future.jdk8.mq.test.queue;

import java.util.Arrays;

import com.future.jdk8.mq.core.Connection;
import com.future.jdk8.mq.core.ConnectionFactory;
import com.future.jdk8.mq.core.MessageProducer;
import com.future.jdk8.mq.core.Session;
import com.future.jdk8.mq.core.SocketMQConnectionFactory;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.domain.Queue;
import com.future.jdk8.mq.test.User;
import com.future.jdk8.mq.test.User.Package;
public class MQProducerQueue {

	public static void main(String[] args) {
        ConnectionFactory connectionFactory = new SocketMQConnectionFactory("tcp://169.254.201.224:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue");
        MessageProducer producer = session.createProducer(queue);
        Message<?> textMessage = session.createMessage(new User("张三",18,Arrays.asList(new Package("哈哈",Arrays.asList(1,2,3)))));
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
	}
}
