package com.docker.compose.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docker.compose.common.CommonResponse;
import com.docker.compose.dto.Product;
import com.docker.compose.dto.User;

@RequestMapping(value="/paramBinder")
@RestController
public class ParamBinderController {
	@RequestMapping(value="/user")
    public CommonResponse<User> user(User user){
        return CommonResponse.success(user);
    }
	
	@RequestMapping(value="/product")
    public CommonResponse<Product> product(Product product){
        return CommonResponse.success(product);
    }
	
}
