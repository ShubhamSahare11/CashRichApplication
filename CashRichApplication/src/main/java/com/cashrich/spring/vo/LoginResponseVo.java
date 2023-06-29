package com.cashrich.spring.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseVo {

	@JsonProperty("session_id")
	private String sessionId;

	@JsonProperty("valid_till")
	private Long validTill;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getValidTill() {
		return validTill;
	}

	public void setValidTill(Long validTill) {
		this.validTill = validTill;
	}

}
