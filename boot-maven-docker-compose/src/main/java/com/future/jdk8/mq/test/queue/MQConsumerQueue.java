package com.future.jdk8.mq.test.queue;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.future.jdk8.mq.core.Connection;
import com.future.jdk8.mq.core.ConnectionFactory;
import com.future.jdk8.mq.core.MessageConsumer;
import com.future.jdk8.mq.core.Session;
import com.future.jdk8.mq.core.SocketMQConnectionFactory;
import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.domain.Queue;
import com.future.jdk8.mq.listener.MessageListener;
import com.future.jdk8.mq.test.User;

public class MQConsumerQueue {
	public static void main(String[] args) throws IOException {
        ConnectionFactory connectionFactory = new SocketMQConnectionFactory("tcp://169.254.201.224:61616");
        Connection connection = connectionFactory.createConnection(7777);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener<User>() {
            @Override
            public void onMessage(Message<User> message) {
            	System.out.println("收到消息:"+JSONObject.toJSONString(message.getContext()));
            }
        });
      	System.in.read();
        consumer.close();
        session.close();
        connection.close();
	}
}
