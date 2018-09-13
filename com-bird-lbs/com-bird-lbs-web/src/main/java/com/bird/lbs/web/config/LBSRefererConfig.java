package com.bird.lbs.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weibo.api.motan.config.springsupport.BasicRefererConfigBean;

@Component("lbsRefererConfig")
@ConfigurationProperties(prefix = "motan.referer.lbs")
public class LBSRefererConfig extends BasicRefererConfigBean {

	private static final long serialVersionUID = -9102380124522146267L;

}
