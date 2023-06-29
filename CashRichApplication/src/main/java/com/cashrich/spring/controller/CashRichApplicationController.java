package com.cashrich.spring.controller;

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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController()
@RequestMapping("/cashrich")
public class CashRichApplicationController {

	private static Logger logger = LoggerFactory.getLogger(CashRichApplicationController.class);

	@Autowired
	CashRichApplicationService service;

	@PostMapping("/signup")
	@ApiOperation(value = "Create a new User", notes = "This will create a new user. Use Internal Controller for Swagger use. This wont work here due to origin header validation.")
	public ResponseEntity<String> userSignUp(@RequestBody UserDetailsVo userDetailsVo) {

		Date startTime = new Date();
		logger.info("Create new user for user {} start time - {}", userDetailsVo.getUserName(), startTime);

		ResponseEntity<String> entity = service.signUpUser(userDetailsVo);

		logger.info("Create new user for user {} finished in - {}ms", userDetailsVo.getUserName(),
				new Date().getTime() - startTime.getTime());
		return entity;
	}

	@PostMapping("/login")
	@ApiResponses({
		@ApiResponse(code = 200, message = "User Logged in"),
		@ApiResponse(code = 204, message = "User not found"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")})	
	@ApiOperation(value = "Login using UserName and Password", notes = "This is used for user login. Use Internal Controller for Swagger use. This wont work here due to origin header validation.")
	public ResponseEntity<Object> userLogin(@RequestBody UserLoginDetailsVo userDetailsLoginVo) {

		Date startTime = new Date();

		if (StringUtils.isAnyBlank(userDetailsLoginVo.getUserName(), userDetailsLoginVo.getPassword())) {
			logger.info("Invalid data");
			return ResponseEntity.badRequest().body("Incomplete Request. Please provide valid data");
		}
		logger.info("Login for user {} start time - {}", userDetailsLoginVo.getUserName(), startTime);

		ResponseEntity<Object> entity = service.loginUser(userDetailsLoginVo);

		logger.info("Login for user {} finished in - {}ms", userDetailsLoginVo.getUserName(),
				new Date().getTime() - startTime.getTime());
		return entity;
	}

	@PostMapping("/getMarketData")
	@ApiOperation(value = "Get CMC data", notes = "This is used to fetch data from CoinMarketCap. Use Internal Controller for Swagger use. This wont work here due to origin header validation.")
	public ResponseEntity<Object> getMarketData(@RequestBody MarketDataRequest marketDataRequest) {

		Date startTime = new Date();

		logger.info("Fetch Data from CoinMarketCap start time - {}", startTime);

		ResponseEntity<Object> entity = service.fetechData(marketDataRequest);

		logger.info("Data fetched successfully in - {}ms", new Date().getTime() - startTime.getTime());
		return entity;
	}
}
