package com.bird.passport.service.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.bird.passport.service.PassportService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

@RunWith(SpringRunner.class)
public class TestPassportServiceImpl extends BaseTest {

	@MotanReferer(basicReferer = "passportRefererConfig")
	private PassportService passportService;

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
	}

	/**
	 * 向"/test"地址发送请求，并打印返回结果
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {

		String result = passportService.accountInfo(899879);

		System.out.println(result);
	}

}
