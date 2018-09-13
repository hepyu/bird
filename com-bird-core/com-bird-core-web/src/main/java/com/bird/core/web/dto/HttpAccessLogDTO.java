package com.bird.core.web.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpAccessLogDTO {

	/**
	 * 请求发生的时间
	 */
	private Date date;

	private String fullUrl;

	private long userId;

	private String httpMethod;

	private String userAgent;

	private String clientIp;

	// spring mvc uri
	private String uri;

	// 单位毫秒
	private long costTime;

	private int status;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getCostTime() {
		return costTime;
	}

	public void setCostTime(long costTime) {
		this.costTime = costTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private static final String httpAccessLogFormat = "%s|%s|%d|%s|%s|%s|%s|%d|%d";

	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

	// 2017-11-22 12:33:32,928 - 1511325212928|
	// /api/syncAppDir/black/get?v=1.0&appId=3&appVersion=1.9.6&gz=1&opVer=35448627285197824&callId=1511325213574&deviceId=ad9894126b7d2efad59dce193aa0940310&sig=abfae689242b0933b0b4ba8ef2804a0a|
	// 0|
	// POST|
	// |
	// 123.59.127.95|
	// syncAppDir.black.get|
	// 0|
	// 0
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return String.format(httpAccessLogFormat//
				, sdf.format(this.date) + "-" + date.getTime() // 时间
				, this.fullUrl// url
				, this.userId // userId
				, this.httpMethod // http method
				, this.userAgent // http agent
				, this.clientIp // clientIp
				, this.uri // mcpMethodName
				, costTime // 消耗的时间
				, status);
	}

}
