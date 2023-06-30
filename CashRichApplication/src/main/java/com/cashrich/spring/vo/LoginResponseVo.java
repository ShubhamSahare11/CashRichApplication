package com.cashrich.spring.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseVo {

	@JsonProperty("session_id")
	private String sessionId;

	@JsonProperty("valid_till")
	private Long validTill;

	@JsonProperty("full_name")
	private String fullName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

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
