package com.bird.passport.web.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bird.lbs.service.LBSService;
import com.bird.passport.service.PassportService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/passport")
public class PassportController {

	@MotanReferer(basicReferer = "passportRefererConfig")
	private PassportService passportService;

	@MotanReferer(basicReferer = "lbsRefererConfig")
	private LBSService lbsService;

	@ApiOperation(value = "获取用户信息", notes = "通过userId获取用户信息")
	@ApiImplicitParam(name = "userId", value = "用户userId", required = true, dataType = "long", paramType = "path")
	@GetMapping("/accountInfo/{userId}")
	@ExceptionHandler
	public String accountInfo(@PathVariable long userId) {

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("userId", userId);
		jsonObj.put("passport.accountInfo", passportService.accountInfo(userId));
		jsonObj.put("lbs.currentLocation", lbsService.currentLocation(userId));

		return jsonObj.toJSONString();
	}

	@ApiOperation(value = "test", notes = "test")
	@ApiImplicitParam(name = "id", value = "用户ID", dataType = "int", paramType = "path")
	@GetMapping(value = "/test/{id}")
	public String test(@PathVariable Integer id) {
		return "test:" + id;
	}
}
