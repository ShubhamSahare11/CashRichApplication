package com.cashrich.spring.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Utility {
	
	private Utility() {
		
	}

	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}

	public static boolean verifyPassword(String password, String dbPass) {
		return BCrypt.checkpw(password, dbPass);
	}
}
