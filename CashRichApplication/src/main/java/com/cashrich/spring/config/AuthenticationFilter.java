package com.cashrich.spring.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cashrich.spring.manager.SessionManager;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private SessionManager sessionManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!shouldSkipAuthentication(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		String sessionToken = extractSessionToken(request);

		if (sessionToken != null && sessionManager.isValidSession(sessionToken)) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private boolean shouldSkipAuthentication(HttpServletRequest request) {
		return "/cashrich/getMarketData".equals(request.getRequestURI());
	}

	private String extractSessionToken(HttpServletRequest request) {
		return request.getHeader("Session-id");
	}

}
