package com.future.fast.fail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private Integer id;
	private String name;
	private Integer age;
	private Double sallary;
	public Employee(String name) {
		super();
		this.name = name;
	}
	
}
