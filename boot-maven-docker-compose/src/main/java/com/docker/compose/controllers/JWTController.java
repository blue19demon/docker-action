package com.docker.compose.controllers;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docker.compose.common.CommonResponse;
import com.docker.compose.tools.JwtUtil;
import com.google.common.collect.Maps;

@RestController
public class JWTController {
	
	@PostMapping(value = "/ssoLogin")
    public CommonResponse<String> ssoLogin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(!("admin".equalsIgnoreCase(username)
        		&&"123".equalsIgnoreCase(password))) {
        	 return CommonResponse.fail("用户名密码有错");
        }
        //这个步骤就是获取user的全部信息不重要，直接忽略
        String userId=UUID.randomUUID().toString();
        String token = createPayLoad(userId);
        return CommonResponse.success(token);
    }
	@PostMapping(value = "/dept/tree")
    public CommonResponse<String> deptTree(HttpServletRequest request, HttpServletResponse response) {
        String depName="测试";
		return CommonResponse.success(depName);
    }
	
	@PostMapping(value = "/dept/tree01")
    public CommonResponse<String> tree01(HttpServletRequest request, HttpServletResponse response) {
		return CommonResponse.fail("504","发生错误");
    }

    /**
     * JWT的组成：Header + payload + signature
     * Payload(载荷)的组成信息，私有声明(标准中注册的声明和公共的声明并未使用)
     * @param userId 用户id
     * @return token
     */
    private String createPayLoad(String userId) {
        Map<String, Object> payload = Maps.newHashMap();
        Date date = new Date();
        // 用户id
        payload.put("uid", userId);
        // 生成时间:当前
        payload.put("iat", date.getTime());
        // 过期时间10分钟(单位毫秒)
        payload.put("ext", date.getTime() + 1000*60*10);
        return JwtUtil.createToken(payload);
    }
}
