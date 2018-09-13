package com.bird.passport.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;

@Component("passportServiceConfig")
@ConfigurationProperties(prefix = "motan.service.passport")
public class PassportServiceConfig extends BasicServiceConfigBean {

	private static final long serialVersionUID = 6113314802171768085L;

}
