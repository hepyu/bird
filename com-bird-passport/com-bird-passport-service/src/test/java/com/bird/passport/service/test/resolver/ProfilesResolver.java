package com.bird.passport.service.test.resolver;

import org.springframework.test.context.ActiveProfilesResolver;

public class ProfilesResolver implements ActiveProfilesResolver {

	@Override
	public String[] resolve(Class<?> aClass) {
		String activeProfiles = System.getProperty("spring.profiles.active");
		return new String[] { activeProfiles != null ? activeProfiles : "test-dev" };
	}

}
