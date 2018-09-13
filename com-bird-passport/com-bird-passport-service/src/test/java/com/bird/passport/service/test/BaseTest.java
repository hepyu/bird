package com.bird.passport.service.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bird.passport.service.BirdPassportServiceMain;
import com.bird.passport.service.test.resolver.ProfilesResolver;

@SpringBootTest(classes = BirdPassportServiceMain.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = ProfilesResolver.class)
//@ImportAutoConfiguration(classes = { PassportRefererConfig.class, UserRefererConfig.class })
public class BaseTest {

}
