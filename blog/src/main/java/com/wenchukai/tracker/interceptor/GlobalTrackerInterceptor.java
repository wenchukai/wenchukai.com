package com.wenchukai.tracker.interceptor;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wenchukai.tracker.Tracker;
import com.wenchukai.tracker.common.Constant;

/**
 * 全局tracker 拦截器 拦截所有请求，包括搜索引擎
 * 
 * @author ChuKai
 *
 */
public class GlobalTrackerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Tracker.track(request);
		validateSessionId((HttpServletRequest) request, (HttpServletResponse) response);
		return super.preHandle(request, response, handler);
	}

	private void validateSessionId(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (Constant.TUID.equals(cookie.getName())) {
					return;
				}
			}
		}
		Cookie cookie = new Cookie(Constant.TUID, UUID.randomUUID().toString());
		cookie.setDomain("wenchukai.com");
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);
	}
}