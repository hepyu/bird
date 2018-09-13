package com.bird.user.service.impl;

import com.bird.user.service.UserService;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService(basicService="userServiceConfig")
public class UserServiceImpl implements UserService{

	@Override
	public String userInfo(long userId) {
		return "userInfo:"+userId+":userInfo.example:hahahaha";
	}

}
