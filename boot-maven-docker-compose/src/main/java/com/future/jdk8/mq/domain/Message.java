package com.future.jdk8.mq.domain;

import java.io.Serializable;

import com.future.jdk8.mq.server.AcknowledgeMode;
import com.future.jdk8.mq.server.DeliverMode;

import lombok.Data;
@Data
public class Message<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DeliverMode deliverMode;
	private AcknowledgeMode autoAcknowledge;
	private Long timestamp;
	private String msgId;
	private T context;
}
