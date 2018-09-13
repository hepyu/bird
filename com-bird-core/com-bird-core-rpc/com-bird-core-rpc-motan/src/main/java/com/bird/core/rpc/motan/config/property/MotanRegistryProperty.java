package com.bird.core.rpc.motan.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "motan.registry")
public class MotanRegistryProperty {

	private String regProtocol = "zookeeper";

	private String name = "registry";

	private String address;

	private Integer connectTimeout = 2000;

	public String getRegProtocol() {
		return regProtocol;
	}

	public void setRegProtocol(String regProtocol) {
		this.regProtocol = regProtocol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}
