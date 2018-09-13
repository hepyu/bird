package com.bird.passport.service.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;

@Component("passportRefererConfig")
@ConfigurationProperties(prefix = "motan.referer.passport")
public class PassportRefererConfig extends BasicRefererConfigBean {

	private static final long serialVersionUID = 7925218544185346404L;

}
