package com.cashrich.spring.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/cashrich/**").allowedOrigins("CashRichFrontend").allowedMethods("GET", "POST", "PUT",
				"DELETE");
	}
}
