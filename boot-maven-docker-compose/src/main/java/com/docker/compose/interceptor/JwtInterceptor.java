package com.docker.compose.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.docker.compose.common.CommonResponse;
import com.docker.compose.enums.TokenState;
import com.docker.compose.tools.JwtUtil;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtInterceptor.class);

    private void output(CommonResponse<String> jsonData,HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean isAjax= isAjaxRequest(request);
    	if(isAjax) {
    	   response.setContentType("text/html;charset=UTF-8;");
           PrintWriter out = response.getWriter();
           out.write(JSONObject.toJSONString(jsonData));
           out.flush();
           out.close();
       }else {
    	   response.sendRedirect("/pages/login");
       }
    }

/***
     * 判断一个请求是否为AJAX请求,是则返回true
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
    	String requestType = request.getHeader("X-Requested-With"); 
    	//如果requestType能拿到值，并且值为 XMLHttpRequest ,表示客户端的请求为异步请求，那自然是ajax请求了，反之如果为null,则是普通的请求 
    	if(requestType == null){
    		return false;
    	}
		return true;
	}
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 前段ajax自定义headers字段，会出现了option请求，在GET请求之前。
        // 所以应该把他过滤掉，以免影响服务。但是不能返回false，如果返回false会导致后续请求不会继续。
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        //从请求头中获取token
        String token = request.getHeader("token");
        LOGGER.info("token------->>>>>>"+token);
        if(StringUtils.isEmpty(token)) {
        	CommonResponse<String> responseDto=CommonResponse.fail("504","token为空");
            LOGGER.warn("token为空");
            output(responseDto,request,response);
            return false;
        }
        Map<String, Object> resultMap = JwtUtil.validToken(token);
        TokenState state = TokenState.getTokenState((String) resultMap.get("state"));
        switch(state) {
            case VALID:
                //　取出payload中数据，放到request作用域中
                request.setAttribute("data", resultMap.get("data"));
                return true;
            case EXPIRED:
            case INVALID:
            	CommonResponse<String> responseDto=CommonResponse.fail("504","您的token不合法或者过期了，请重新登陆");
                LOGGER.warn("无效token");
                output(responseDto,request,response);
                break;
            default:
                break;
        }
        return false;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
}