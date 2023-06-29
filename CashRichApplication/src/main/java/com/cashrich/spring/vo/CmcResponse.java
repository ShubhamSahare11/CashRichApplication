package com.cashrich.spring.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CmcResponse {
	@JsonProperty("data")
	private Map<String, CoinDetails> data;

	public Map<String, CoinDetails> getData() {
		return data;
	}

	public void setData(Map<String, CoinDetails> data) {
		this.data = data;
	}

}
