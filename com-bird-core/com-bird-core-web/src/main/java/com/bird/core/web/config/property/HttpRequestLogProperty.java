package com.bird.core.web.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "http.request.log")
public class HttpRequestLogProperty {

	private boolean enableAccess = true;

	private boolean enableAccessSlow = true;;

	private boolean enableAccessNZ = true;

	private boolean enableAccessStat = true;

	// 毫秒，默认值200毫秒
	private long slowThreshold = 200;

	// 毫秒,默认值2秒
	private long slowXThreshold = 200 * 10;

	// 毫秒，默认值20秒
	private long slowXXThreshold = 200 * 100;

	public long getSlowThreshold() {
		return slowThreshold;
	}

	public void setSlowThreshold(long slowThreshold) {
		this.slowThreshold = slowThreshold;
	}

	public long getSlowXThreshold() {
		return slowXThreshold;
	}

	public void setSlowXThreshold(long slowXThreshold) {
		this.slowXThreshold = slowXThreshold;
	}

	public long getSlowXXThreshold() {
		return slowXXThreshold;
	}

	public void setSlowXXThreshold(long slowXXThreshold) {
		this.slowXXThreshold = slowXXThreshold;
	}

	public boolean isEnableAccess() {
		return enableAccess;
	}

	public void setEnableAccess(boolean enableAccess) {
		this.enableAccess = enableAccess;
	}

	public boolean isEnableAccessSlow() {
		return enableAccessSlow;
	}

	public void setEnableAccessSlow(boolean enableAccessSlow) {
		this.enableAccessSlow = enableAccessSlow;
	}

	public boolean isEnableAccessNZ() {
		return enableAccessNZ;
	}

	public void setEnableAccessNZ(boolean enableAccessNZ) {
		this.enableAccessNZ = enableAccessNZ;
	}

	public boolean isEnableAccessStat() {
		return enableAccessStat;
	}

	public void setEnableAccessStat(boolean enableAccessStat) {
		this.enableAccessStat = enableAccessStat;
	}

}
