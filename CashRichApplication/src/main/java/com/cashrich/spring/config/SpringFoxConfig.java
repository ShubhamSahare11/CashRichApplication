package com.cashrich.spring.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cashrich")).paths(PathSelectors.any()).build()
				.apiInfo(apiDoc()).useDefaultResponseMessages(false);
	}

	private ApiInfo apiDoc() {
		// TODO Auto-generated method stub
		return new ApiInfo("CashRich Application", "APIs for CashRich Application", "0,0,1", "Open-Usage",
				new Contact("Shubham Sahare", "", "shubhamsahare11@gmail.com"), "No License", "http://127.0.0.1:8080",
				Collections.emptyList());
	}
}
