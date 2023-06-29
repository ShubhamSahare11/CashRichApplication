package com.cashrich.spring.controller.internal;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashrich.spring.service.CashRichApplicationService;
import com.cashrich.spring.vo.MarketDataRequest;
import com.cashrich.spring.vo.UserDetailsVo;
import com.cashrich.spring.vo.UserLoginDetailsVo;

import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/cashrich/internal")
public class CashRichApplicationControllerInternal {

	private static Logger logger = LoggerFactory.getLogger(CashRichApplicationControllerInternal.class);

	@Autowired
	CashRichApplicationService service;

	@PostMapping("/signup")
	@ApiOperation(value = "Create a new User", notes = "This will create a new user")
	public ResponseEntity<String> userSignUp(@RequestBody UserDetailsVo userDetailsVo) {

		Date startTime = new Date();
		logger.info("Create new user for user {} start time - {}", userDetailsVo.getUserName(), startTime);

		ResponseEntity<String> entity = service.signUpUser(userDetailsVo);

		logger.info("Create new user for user {} finished in - {}ms", userDetailsVo.getUserName(),
				new Date().getTime() - startTime.getTime());
		return entity;
	}

	@PostMapping("/login")
	@ApiOperation(value = "Login using UserName and Password", notes = "This is used for user login")
	public ResponseEntity<?> userLogin(@RequestBody UserLoginDetailsVo userDetailsLoginVo) {

		Date startTime = new Date();

		if (StringUtils.isAnyBlank(userDetailsLoginVo.getUserName(), userDetailsLoginVo.getPassword())) {
			logger.info("Invalid data");
			return ResponseEntity.badRequest().body("Incomplete Request. Please provide valid data");
		}
		logger.info("Login for user {} start time - {}", userDetailsLoginVo.getUserName(), startTime);

		ResponseEntity<?> entity = service.loginUser(userDetailsLoginVo);

		logger.info("Login for user {} finished in - {}ms", userDetailsLoginVo.getUserName(),
				new Date().getTime() - startTime.getTime());
		return entity;
	}

	@PostMapping("/getMarketData")
	@ApiOperation(value = "Get CMC data", notes = "This is used to fetch data from CoinMarketCap")
	public ResponseEntity<?> getMarketData(@RequestBody MarketDataRequest marketDataRequest) {

		Date startTime = new Date();

		logger.info("Fetch Data from CoinMarketCap start time - {}", startTime);

		ResponseEntity<?> entity = service.fetechData(marketDataRequest);

		logger.info("Data fetched successfully in - {}ms", new Date().getTime() - startTime.getTime());
		return entity;
	}
}
