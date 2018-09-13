package com.bird.passport.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;

@Component("lbsRefererConfig")
@ConfigurationProperties(prefix = "motan.referer.lbs")
public class LBSRefererConfig extends BasicRefererConfigBean {

	private static final long serialVersionUID = -4521814980567403590L;

}
