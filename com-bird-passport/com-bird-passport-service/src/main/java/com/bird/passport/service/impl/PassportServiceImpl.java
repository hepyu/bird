package com.bird.passport.service.impl;

import com.bird.passport.service.PassportService;
import com.bird.user.service.UserService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService(basicService = "passportServiceConfig")
public class PassportServiceImpl implements PassportService {

	@MotanReferer(basicReferer = "userRefererConfig")
	private UserService userService;

	@Override
	public String accountInfo(long userId) {
		return userService.userInfo(userId);
	}
}
