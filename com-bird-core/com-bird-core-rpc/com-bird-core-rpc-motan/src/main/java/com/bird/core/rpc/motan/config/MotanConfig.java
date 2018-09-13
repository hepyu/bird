package com.bird.core.rpc.motan.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weibo.api.motan.config.springsupport.AnnotationBean;

@Configuration
@ImportAutoConfiguration(classes = {MotanRegistryConfig.class})
public class MotanConfig {

	@Bean
	public AnnotationBean motanAnnotationBean() {
	     AnnotationBean motanAnnotationBean = new AnnotationBean();
	     motanAnnotationBean.setPackage("com.bird");
	     return motanAnnotationBean;
	}
}
