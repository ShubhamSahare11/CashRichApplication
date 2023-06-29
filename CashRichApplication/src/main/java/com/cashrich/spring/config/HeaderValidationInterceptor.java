package com.cashrich.spring.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HeaderValidationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (shouldSkipAuthentication(request)) {
			return true;
		}
		String headerValue = request.getHeader("Origin");
		if ("CashRichFrontend".equals(headerValue)) {
			return true;
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid header value for Origin");
			return false;
		}
	}

	private boolean shouldSkipAuthentication(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/cashrich/internal");
	}
	// Implement other methods of the HandlerInterceptor interface if needed

}
