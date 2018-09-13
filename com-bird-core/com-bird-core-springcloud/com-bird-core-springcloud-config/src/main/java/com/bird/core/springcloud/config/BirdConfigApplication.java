package com.bird.core.springcloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server 配置中心
 * 
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class BirdConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirdConfigApplication.class, args);
	}

}
