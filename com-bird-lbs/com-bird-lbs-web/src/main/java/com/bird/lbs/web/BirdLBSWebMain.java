package com.bird.lbs.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bird.core.rpc.motan.config.MotanConfig;
import com.bird.core.web.config.InterceptorConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@ImportResource(locations={"${motan.xml:classpath*:motan*.xml}"})
@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
@ImportAutoConfiguration(classes = { MotanConfig.class, InterceptorConfig.class })
public class BirdLBSWebMain {

	private static final Log logger = LogFactory.getLog(BirdLBSWebMain.class);

	public static void main(String[] args) {
		try {
			SpringApplication.run(BirdLBSWebMain.class, args);
			System.out.println("client started.");
		} catch (BeanCreationException e) {
			logger.fatal("error create bean occur: shutdown.", e);
			System.exit(-1);
		}
	}
}
