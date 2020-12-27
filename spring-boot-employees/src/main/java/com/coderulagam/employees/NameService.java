package com.coderulagam.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NameService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	public String callNameService() {
		
		CircuitBreaker cb = circuitBreakerFactory.create("nameservicebreaker");
		
		
		return cb.run(() -> restTemplate.getForObject("http://localhost:8081/api/name", String.class), throwable -> fallbackNameService());
		
		//String name = restTemplate.getForObject("http://localhost:8081/api/name", String.class);
		
		//return name;
	}
	
	public String fallbackNameService() {
		return "fall back name";
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	

}
