package com.docker.compose.controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docker.compose.common.CommonResponse;
import com.docker.compose.service.TestService;

@RestController
@RequestMapping(value = "/test")
public class OrderController {

    @Resource(name = "testServiceImpl01")
    private TestService userService01;
    @Resource(name = "testServiceImpl02")
    private TestService userService02;
    @Resource(name = "testServiceImpl03")
    private TestService userService03;
    @Resource(name = "testServiceImpl04")
    private TestService userService04;
    @Resource(name = "testServiceImpl05")
    private TestService userService05;

    @RequestMapping("/insert01")
    public CommonResponse<?> test1() {
        //同时调用parent和child
    	userService01.parent();
    	userService01.child();
        return CommonResponse.success();
    }
    
    @RequestMapping("/insert02")
    public CommonResponse<?> test2() {
        //调用parent
    	userService02.parent();
        return CommonResponse.success();
    }
    
    @RequestMapping("/insert03")
    public CommonResponse<?> test3() {
    	//调用parent
    	userService03.parent();
        return CommonResponse.success();
    }
    
    @RequestMapping("/insert04")
    public CommonResponse<?> test4() {
    	//调用parent
    	userService04.parent();
        return CommonResponse.success();
    }
    

    @RequestMapping("/insert05")
    public CommonResponse<?> test5() {
    	//调用parent
    	userService05.parent();
        return CommonResponse.success();
    }
}