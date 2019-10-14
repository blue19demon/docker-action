package com.future.jdk8.mq.test;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;
	private List<Package> packages;
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Package implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String tag;
		private List<Integer> content;
	}
}

