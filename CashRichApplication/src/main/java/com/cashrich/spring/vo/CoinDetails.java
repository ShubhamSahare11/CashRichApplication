package com.cashrich.spring.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinDetails {

	@JsonProperty("cmc_rank")
	private Integer rank;

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("quote")
	private Map<String, QuoteInCurrency> quote;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Map<String, QuoteInCurrency> getQuote() {
		return quote;
	}

	public void setQuote(Map<String, QuoteInCurrency> quote) {
		this.quote = quote;
	}

}
