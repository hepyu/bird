package com.bird.core.rpc.motan.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "motan.shutdownhook")
public class MotanShutdownHookProperty {

	/**
	 * 当把motan从注册中心移除后等待指定时间后停止JVM
	 * 
	 * 单位是秒
	 */
	private Integer waitTimeAfterOffline = 20;

	public Integer getWaitTimeAfterOffline() {
		return waitTimeAfterOffline;
	}

	public void setWaitTimeAfterOffline(Integer waitTimeAfterOffline) {
		this.waitTimeAfterOffline = waitTimeAfterOffline;
	}

}
