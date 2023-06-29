package com.cashrich.spring.service;

import org.springframework.http.ResponseEntity;

import com.cashrich.spring.vo.MarketDataRequest;
import com.cashrich.spring.vo.UserDetailsVo;
import com.cashrich.spring.vo.UserLoginDetailsVo;

public interface CashRichApplicationService {
	public ResponseEntity<String> signUpUser(UserDetailsVo userDetailsVo);

	public ResponseEntity<Object> loginUser(UserLoginDetailsVo userDetailsLoginVo);
	
	public ResponseEntity<Object> fetechData(MarketDataRequest marketDataRequest);
}
