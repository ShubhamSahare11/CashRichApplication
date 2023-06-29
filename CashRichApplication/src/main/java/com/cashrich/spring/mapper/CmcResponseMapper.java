package com.cashrich.spring.mapper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cashrich.spring.vo.CoinDetails;
import com.cashrich.spring.vo.MarketDataResponseVo;

@Component
public class CmcResponseMapper {

	public List<MarketDataResponseVo> mapCmcResponse(Map<String, CoinDetails> data) {

		List<MarketDataResponseVo> coinList = new ArrayList<>();
		data.keySet().forEach(cmcCoin -> {
			MarketDataResponseVo coin = new MarketDataResponseVo();
			coin.setRank(data.get(cmcCoin).getRank());
			coin.setSymbol(data.get(cmcCoin).getSymbol());
			if (data.get(cmcCoin).getQuote().containsKey("USD")) {
				Double price = data.get(cmcCoin).getQuote().get("USD").getPrice();
				Double volume = data.get(cmcCoin).getQuote().get("USD").getVolumeHr24();
				DecimalFormat format = new DecimalFormat("0.00");
				coin.setPrice("$".concat(format.format(price)));
				coin.setVolumeHr24(format.format(volume));
			}
			coinList.add(coin);
		});
		return coinList;
	}

}
