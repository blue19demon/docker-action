package com.future.jdk8.mq.core;

public interface ConnectionFactory {

	Connection createConnection(Integer listenerPort);

	Connection createConnection();

}
