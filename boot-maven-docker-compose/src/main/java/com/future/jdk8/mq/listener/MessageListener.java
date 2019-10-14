package com.future.jdk8.mq.listener;

import com.future.jdk8.mq.domain.Message;
import com.future.jdk8.mq.exception.JMSException;

public interface MessageListener<T> {

	void onMessage(Message<T> message) throws JMSException;

}
