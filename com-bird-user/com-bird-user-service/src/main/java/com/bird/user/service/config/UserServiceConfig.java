package com.bird.user.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;

@Component("userServiceConfig")
@ConfigurationProperties(prefix = "motan.service.user")
public class UserServiceConfig extends BasicServiceConfigBean {

	private static final long serialVersionUID = 7178649019744891526L;

}