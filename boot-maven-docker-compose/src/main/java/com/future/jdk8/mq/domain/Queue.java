package com.future.jdk8.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Queue implements CommunicationWay{
	private String name;

	@Override
	public String name() {
		return name;
	}
}
