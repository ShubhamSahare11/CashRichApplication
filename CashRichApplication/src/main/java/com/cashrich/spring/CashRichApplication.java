package com.cashrich.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.cashrich")
public class CashRichApplication {
	
	private static Logger logger = LoggerFactory.getLogger(CashRichApplication.class);

	public static void main(String[] args) {
		logger.info("Starting Spring Boot Application");
		SpringApplication.run(CashRichApplication.class, args);
	}

}
