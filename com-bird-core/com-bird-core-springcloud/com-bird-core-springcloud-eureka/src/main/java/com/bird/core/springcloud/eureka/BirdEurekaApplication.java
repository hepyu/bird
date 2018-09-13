package com.bird.core.springcloud.eureka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server 中心
 * 
 */
@EnableEurekaServer
@SpringBootApplication
public class BirdEurekaApplication {

	public static void main(String[] args) {
		// 官方文档建议：
		// new SpringApplicationBuilder(Application.class).web(true).run(args);
		// 但是这个方法已经废弃，用下边的代替。
		new SpringApplicationBuilder(BirdEurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
	}

}
