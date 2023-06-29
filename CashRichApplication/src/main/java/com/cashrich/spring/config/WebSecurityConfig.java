package com.cashrich.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	@Autowired
	AuthenticationFilter authenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint()).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeHttpRequests(authz -> authz
				.antMatchers("/cashrich/signup", "/cashrich/login", "/v2/api-docs", "/swagger-ui.html",
						"/swagger-ui/**")
				.permitAll().antMatchers("cashrich/getMarketData").authenticated().antMatchers("/**").permitAll())
				.antMatcher("/**/**").httpBasic();
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers("/v2/api-docs", "/swagger-ui.html", "/swagger-ui/**",
				"/cashrich/signup", "/cashrich/login");
	}

}
