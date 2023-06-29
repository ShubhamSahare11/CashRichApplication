package com.cashrich.spring.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteInCurrency {

	@JsonProperty("price")
	private Double price;

	@JsonProperty("volume_24h")
	private Double volumeHr24;

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
