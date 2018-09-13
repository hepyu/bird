package com.bird.passport.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.bird.core.rpc.motan.config.MotanConfig;
import com.bird.core.rpc.motan.config.property.MotanShutdownHookProperty;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

//@ImportResource(locations={"${motan.xml:classpath*:motan*.xml}"})
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@ImportAutoConfiguration(classes = { MotanConfig.class })
public class BirdPassportServiceMain {

	private static final Log logger = LogFactory.getLog(BirdPassportServiceMain.class);

	public static void main(String[] args) {
		try {
			ApplicationContext context = SpringApplication.run(BirdPassportServiceMain.class, args);
			// 在使用注册中心时要主动调用下面代码
			MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
			System.out.println("server started.");

			final MotanShutdownHookProperty mshp = context.getBean(MotanShutdownHookProperty.class);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, false);
						if (mshp != null && mshp.getWaitTimeAfterOffline() != null) {
							logger.info("has offline the service from registery, wait time to close: "
									+ mshp.getWaitTimeAfterOffline() + " seconds.");
							Thread.sleep(mshp.getWaitTimeAfterOffline() * 1000);
						} else {
							logger.error("error config MotanShutdownHookProperties.");
						}
						logger.info("be about to close.");
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			});
		} catch (BeanCreationException e) {
			logger.fatal("error create bean occur: shutdown.", e);
			System.exit(-1);
		}
	}

}
