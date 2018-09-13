package com.bird.passport.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;

@Component("userRefererConfig")
@ConfigurationProperties(prefix = "motan.referer.user")
public class UserRefererConfig extends BasicRefererConfigBean {

	private static final long serialVersionUID = -4521814980567403590L;

}
