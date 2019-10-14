package com.future.jdk8.jms;

import java.io.Serializable;

import lombok.Data;

@Data
public class MessageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mode mode;
	private String name;
	private Object content;
}
