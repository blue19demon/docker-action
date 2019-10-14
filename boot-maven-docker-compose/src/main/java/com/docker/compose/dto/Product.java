package com.docker.compose.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Product extends BaseDto{

	private String name;
	
}
