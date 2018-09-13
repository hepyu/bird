package com.bird.lbs.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bird.lbs.service.LBSService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * springmvc风格
 * 
 * @author Administrator
 *
 */
@RestController()
@RequestMapping("/lbs")
public class LBSController {

	@MotanReferer(basicReferer = "lbsRefererConfig")
	private LBSService lbsService;

	@ApiOperation(value = "获取当前坐标", notes = "获取指定用户的当前坐标")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "long", paramType = "path")
	@GetMapping("/currentLocation/{userId}")
	public String currentLocation(@PathVariable long userId) {
		return lbsService.currentLocation(userId);
	}

}
