package com.docker.compose.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.docker.compose.common.CommonResponse;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(value="/elk")
@RestController
@Slf4j
public class TestController {
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
 
    @RequestMapping(value="/data")
    public CommonResponse<List<Map<String,Object>>> data(){
    	List<Map<String,Object>> data = getvaue();
    	log.info("ELK测试，数据："+JSONObject.toJSONString(data));
        return CommonResponse.success(data);
    }
 
    public List<Map<String,Object>> getvaue(){
        String sql="SELECT * FROM person";
        return jdbcTemplate.queryForList(sql);
    }
}