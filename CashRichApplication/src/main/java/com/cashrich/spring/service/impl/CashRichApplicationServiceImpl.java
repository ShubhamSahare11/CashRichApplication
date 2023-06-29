package com.cashrich.spring.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cashrich.spring.manager.SessionManager;
import com.cashrich.spring.mapper.UserDetailsMapper;
import com.cashrich.spring.model.UserDetails;
import com.cashrich.spring.repository.UserDetailsRepository;
import com.cashrich.spring.service.CashRichApplicationService;
import com.cashrich.spring.vo.CmcResponse;
import com.cashrich.spring.vo.CoinDetails;
import com.cashrich.spring.vo.LoginResponseVo;
import com.cashrich.spring.vo.MarketDataRequest;
import com.cashrich.spring.vo.MarketDataResponseVo;
import com.cashrich.spring.vo.UserDetailsVo;
import com.cashrich.spring.vo.UserLoginDetailsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CashRichApplicationServiceImpl implements CashRichApplicationService {

	@Autowired
	UserDetailsRepository repository;
	@Autowired
	UserDetailsMapper mapper;
	@Autowired
	private SessionManager sessionManager;

	@Value("${cashrich.cmc.url}")
	private String cmcUrl;
	@Value("${cashrich.cmc.header.key}")
	private String cmckey;
	@Value("${cashrich.cmc.header.value}")
	private String cmcValue;

	private static Logger logger = LoggerFactory.getLogger(CashRichApplicationServiceImpl.class);

	private ObjectMapper objMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
			false);

	public ResponseEntity<String> signUpUser(UserDetailsVo userDetailsVo) {

		try {
			Optional<UserDetails> user = repository.findById(userDetailsVo.getUserName());
			if (user.isPresent()) {
				logger.info("User already exists!");
				return ResponseEntity.badRequest().body("Username already exists. Please try another.");
			}
			repository.save(mapper.mapUserDetailsBo(userDetailsVo));
			logger.info("User profile created");
			return ResponseEntity.ok().body("User successfully signed up!");
		} catch (Exception e) {
			logger.error("Error occurred while User Signup {}", e.getMessage());
			return ResponseEntity.internalServerError().body("Error occurred while User Signup");
		}
	}

	@Override
	public ResponseEntity<Object> loginUser(UserLoginDetailsVo userDetailsLoginVo) {
		try {
			Optional<UserDetails> user = repository.findById(userDetailsLoginVo.getUserName());
			if (user.isEmpty()) {
				logger.info("User Not Found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username/Password does not match");
			}
			UserDetails userDetails = user.get();
			if (!userDetailsLoginVo.getPassword().equals(userDetails.getPassword())) {
				logger.info("Password does not match");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username/Password does not match");
			}

			LoginResponseVo loginResponse = mapper.generateLoginResponse(userDetails);
			sessionManager.createSession(loginResponse.getSessionId(), userDetailsLoginVo);
			return ResponseEntity.ok(loginResponse);

		} catch (Exception e) {
			logger.error("Error occurred while User Login {}", e.getMessage());
			return ResponseEntity.internalServerError().body("Error occurred while User Login");
		}
	}

	@Override
	public ResponseEntity<Object> fetechData(MarketDataRequest marketDataRequest) {

		try {
			if (null == marketDataRequest || CollectionUtils.isEmpty(marketDataRequest.getCoins())) {
				logger.info("Null object/Empty Coin List");
				return ResponseEntity.badRequest().body("Invalid data");
			}
			String coins = marketDataRequest.getCoins().stream().collect(Collectors.joining(","));
			String url = MessageFormat.format(cmcUrl, coins);
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add(cmckey, cmcValue);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
			if (!response.hasBody()) {
				logger.info("Empty Response from the API");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data Found");
			}

			response.getBody();
			CmcResponse responseObj = objMapper.readValue(response.getBody(), CmcResponse.class);
			Map<String, CoinDetails> data = responseObj.getData();
			List<MarketDataResponseVo> coinList = new ArrayList<>();
			data.keySet().forEach(cmcCoin -> {
				MarketDataResponseVo coin = new MarketDataResponseVo();
				coin.setRank(data.get(cmcCoin).getRank());
				coin.setSymbol(data.get(cmcCoin).getSymbol());
				if (data.get(cmcCoin).getQuote().containsKey("USD")) {
					coin.setPrice(data.get(cmcCoin).getQuote().get("USD").getPrice());
					coin.setVolumeHr24(data.get(cmcCoin).getQuote().get("USD").getVolumeHr24());
				}
				coinList.add(coin);
			});
			return ResponseEntity.ok().body(coinList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

}
