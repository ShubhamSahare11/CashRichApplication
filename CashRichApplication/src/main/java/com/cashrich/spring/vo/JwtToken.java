package com.cashrich.spring.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {

	@JsonProperty("jwt_token")
	public String jwtToken;

	public JwtToken(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
