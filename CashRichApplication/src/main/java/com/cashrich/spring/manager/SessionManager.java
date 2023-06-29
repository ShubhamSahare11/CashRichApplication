package com.cashrich.spring.manager;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cashrich.spring.vo.UserLoginDetailsVo;

@Component
public class SessionManager {
	private Map<String, UserLoginDetailsVo> sessionMap = new ConcurrentHashMap<>();
	private final Map<String, Instant> sessionStartTimeMap = new ConcurrentHashMap<>();
	private static final int SESSION_TIMEOUT_SECONDS = 300; // 5 minutes

	public void createSession(String sessionToken, UserLoginDetailsVo user) {
		sessionMap.put(sessionToken, user);
		sessionStartTimeMap.put(sessionToken, Instant.now());
	}

	public UserLoginDetailsVo getSessionUser(String sessionToken) {
		return sessionMap.get(sessionToken);
	}

	public void removeSession(String sessionToken) {
		sessionMap.remove(sessionToken);
		sessionStartTimeMap.remove(sessionToken);
	}

	public boolean isValidSession(String sessionToken) {
		Instant sessionStartTime = sessionStartTimeMap.get(sessionToken);
		if (sessionStartTime != null) {
			Instant currentTime = Instant.now();
			Duration sessionDuration = Duration.between(sessionStartTime, currentTime);
			if (sessionDuration.getSeconds() <= SESSION_TIMEOUT_SECONDS) {
				// Session is still valid
				return true;
			} else {
				// Session has expired, remove it
				removeSession(sessionToken);
				return false;
			}
		}
		return false;
	}

}
