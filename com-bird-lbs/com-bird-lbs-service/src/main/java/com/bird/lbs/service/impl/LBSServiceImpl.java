package com.bird.lbs.service.impl;

import com.bird.lbs.service.LBSService;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService(basicService = "lbsServiceConfig")
public class LBSServiceImpl implements LBSService {

	@Override
	public String currentLocation(long userId) {
		return "userId:" + userId + ";location:x,y";
	}

}
