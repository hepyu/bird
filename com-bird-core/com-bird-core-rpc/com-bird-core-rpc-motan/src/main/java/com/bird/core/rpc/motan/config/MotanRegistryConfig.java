package com.bird.core.rpc.motan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bird.core.rpc.motan.config.property.MotanProtocolProperty;
import com.bird.core.rpc.motan.config.property.MotanRegistryProperty;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;

@Configuration
public class MotanRegistryConfig {

	@Autowired
	private MotanProtocolProperty motanProtocolProperties;

	@Autowired
	private MotanRegistryProperty motanRegistryProperties;

	@Bean(name = "motan")
	public ProtocolConfigBean protocolConfig() {
		ProtocolConfigBean config = new ProtocolConfigBean();
		config.setDefault(motanProtocolProperties.getIsDefault());
		config.setName(motanProtocolProperties.getName());
		config.setMaxContentLength(motanProtocolProperties.getMaxContentLength());
		return config;
	}

	@Bean(name = "registryConfig")
	public RegistryConfigBean registryConfig() {
		RegistryConfigBean config = new RegistryConfigBean();
		config.setRegProtocol(motanRegistryProperties.getRegProtocol());
		config.setName(motanRegistryProperties.getName());
		config.setAddress(motanRegistryProperties.getAddress());
		config.setConnectTimeout(motanRegistryProperties.getConnectTimeout());
		return config;
	}
}
