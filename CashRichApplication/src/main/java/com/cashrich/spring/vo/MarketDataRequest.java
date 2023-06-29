package com.cashrich.spring.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketDataRequest {

	@JsonProperty("coins")
	List<String> coins;

	public List<String> getCoins() {
		return coins;
	}

	public void setCoins(List<String> coins) {
		this.coins = coins;
	}
	
	
}
