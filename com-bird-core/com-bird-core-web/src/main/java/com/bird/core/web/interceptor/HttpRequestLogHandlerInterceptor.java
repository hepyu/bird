package com.bird.core.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bird.core.web.config.property.HttpRequestLogProperty;
import com.bird.core.web.dto.HttpAccessLogDTO;
import com.bird.core.web.util.HttpUtil;

@Configuration
public class HttpRequestLogHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Log logger = LogFactory.getLog(HttpRequestLogHandlerInterceptor.class);
	private static final Log httpAccessLogger = LogFactory.getLog("http_access");
	private static final Log httpAccessNZLogger = LogFactory.getLog("http_access_nz");
	private static final Log httpAccessSlowLogger = LogFactory.getLog("http_access_slow");
	private static final Log httpAccessSlowXLogger = LogFactory.getLog("http_access_slowX");
	private static final Log httpAccessSlowXXLogger = LogFactory.getLog("http_access_slowXX");
	private static final Log httpAccessStatLogger = LogFactory.getLog("http_access_stat");

	// private static final Log httpAccessErrorLogger =
	// LogFactory.getLog("http_access_error");

	@Autowired
	private HttpRequestLogProperty httpRequestLogProperty;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			if (httpRequestLogProperty.isEnableAccess()) {
				Date date = new Date();
				String realIp = HttpUtil.getClientRealIp(request);
				request.setAttribute("client_real_ip", realIp);
				request.setAttribute("begin_time", date.getTime());
				request.setAttribute("date", date);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// httpAccessErrorLogger.error(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
			if (httpRequestLogProperty.isEnableAccess()) {
				long beginTime = (Long) request.getAttribute("begin_time");
				String realIp = (String) request.getAttribute("client_real_ip");
				Date date = (Date) request.getAttribute("date");
				long costTime = System.currentTimeMillis() - beginTime;
				String uri = request.getRequestURI();

				// 每条request都要写入accessLog，不论成功还是失败.
				HttpAccessLogDTO accessLog = new HttpAccessLogDTO();
				accessLog.setClientIp(realIp);
				accessLog.setCostTime(costTime);
				accessLog.setFullUrl(HttpUtil.getFullUrl(request));
				accessLog.setHttpMethod(request.getMethod());
				accessLog.setStatus(response.getStatus());
				accessLog.setDate(date);
				accessLog.setUri(uri);
				accessLog.setUserAgent(HttpUtil.getUserAgent(request));
				// accessLog.setUserId(userId);
				httpAccessLogger.info(accessLog);

				if (httpRequestLogProperty.isEnableAccessSlow()) {
					if (costTime < httpRequestLogProperty.getSlowThreshold()) {
						// nothing
					} else if (httpRequestLogProperty.getSlowThreshold() <= costTime
							&& costTime < httpRequestLogProperty.getSlowXThreshold()) {
						httpAccessSlowLogger.info(accessLog);
					} else if (httpRequestLogProperty.getSlowXThreshold() <= costTime
							&& costTime < httpRequestLogProperty.getSlowXXThreshold()) {
						httpAccessSlowXLogger.info(accessLog);
					} else if (httpRequestLogProperty.getSlowXXThreshold() <= costTime) {
						httpAccessSlowXXLogger.info(accessLog);
					}
				}

				if (response.getStatus() != HttpStatus.OK.value()) {
					if (httpRequestLogProperty.isEnableAccessNZ()) {
						httpAccessNZLogger.info(accessLog);
					}
				}

				// if (httpRequestLogProperty.isEnableAccessStat()) {
				// TODO
				// }
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// httpAccessErrorLogger.error(e.getMessage(), e);
		}
	}

}
