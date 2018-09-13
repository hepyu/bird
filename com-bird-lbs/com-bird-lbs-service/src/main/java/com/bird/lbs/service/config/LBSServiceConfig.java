package com.bird.lbs.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;

@Component("lbsServiceConfig")
@ConfigurationProperties(prefix = "motan.service.lbs")
public class LBSServiceConfig extends BasicServiceConfigBean {

	private static final long serialVersionUID = -8563227212748091579L;

}