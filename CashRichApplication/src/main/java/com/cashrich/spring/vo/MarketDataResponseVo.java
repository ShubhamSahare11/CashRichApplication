package com.cashrich.spring.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketDataResponseVo {

	@JsonProperty("rank")
	private Integer rank;

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("price")
	private Double price;

	@JsonProperty("volume_24h")
	private Double volumeHr24;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String string) {
		this.symbol = string;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getVolumeHr24() {
		return volumeHr24;
	}

	public void setVolumeHr24(Double volumeHr24) {
		this.volumeHr24 = volumeHr24;
	}

}
