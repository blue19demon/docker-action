package com.future.jdk8.jms;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClientInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private Integer port;
}
