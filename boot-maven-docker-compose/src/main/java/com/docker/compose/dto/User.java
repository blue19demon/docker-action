package com.docker.compose.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseDto{

	private String no;
	
}
