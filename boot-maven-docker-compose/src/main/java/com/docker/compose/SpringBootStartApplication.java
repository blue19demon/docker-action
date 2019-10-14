package com.docker.compose;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
 * 用于MAVEN编译的初始化器
 * <p>
 * 修改启动类,继承SpringBootServletInitializer并重写configure方法
 * 
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意:这里要指向用main方法执行的Application启动类
        return builder.sources(BootMavenDockerComposeApplication.class);
    }
	
}
