package com.docker.compose.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * 参数绑定拦截器
 * 
 * 给DTO对象绑定参数拦截器
 */
@Component
@Slf4j
public class ParamBinderInterceptor extends HandlerInterceptorAdapter {

	private static final String FIELD_NAME = "accessControll";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String accessUri = request.getRequestURI().substring(request.getContextPath().length());
		log.info("accessUri=" + accessUri);
		String manageRule = request.getHeader(FIELD_NAME);

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if (map == null) {
			map = new HashMap<>();
		}
		map.put(FIELD_NAME, manageRule);
		request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, map);
		//重写方法
		return super.preHandle(request, response, handler);
	}

}
