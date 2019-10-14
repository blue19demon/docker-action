package com.docker.compose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EmbeddedTomcatConfiguration.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class BootMavenDockerComposeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootMavenDockerComposeApplication.class, args);
	}

}
