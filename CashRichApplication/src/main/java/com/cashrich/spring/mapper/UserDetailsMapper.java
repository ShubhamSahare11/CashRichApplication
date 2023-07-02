package com.cashrich.spring.mapper;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.cashrich.spring.model.UserDetails;
import com.cashrich.spring.utils.Utility;
import com.cashrich.spring.vo.LoginResponseVo;
import com.cashrich.spring.vo.UserDetailsVo;

@Component
public class UserDetailsMapper {
	public UserDetails mapUserDetailsBo(UserDetailsVo userDetailsVo) {

		UserDetails newUser = new UserDetails();
		Date date = new Date();
		newUser.setDateCreated(date);
		newUser.setDateModified(date);
		newUser.setFirstName(userDetailsVo.getFirstName());
		newUser.setLastName(userDetailsVo.getLastName());
		String hashPassword = Utility.hashPassword(userDetailsVo.getPassword());
		newUser.setPassword(hashPassword);
		newUser.setUserName(userDetailsVo.getUserName());

		return newUser;
	}

	public LoginResponseVo generateLoginResponse(UserDetails userDetails) {
		LoginResponseVo loginResponse = new LoginResponseVo();
		loginResponse.setSessionId(UUID.randomUUID().toString());
		loginResponse.setValidTill(new Date(Calendar.getInstance().getTimeInMillis() + (10 * 60 * 1000)).getTime());
		if (null != userDetails.getFirstName()) {
			loginResponse.setFullName(userDetails.getFirstName().concat(" ").concat(userDetails.getLastName()));
		} else {
			loginResponse.setFullName(userDetails.getLastName());
		}
		return loginResponse;
	}
}
