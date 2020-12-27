package com.coderulagam.employees.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4JConfig {
	
	@Bean
	public TimeLimiterConfig timeLimiterConfig() {
		return TimeLimiterConfig.custom()
				  .timeoutDuration(Duration.ofSeconds(4))
				  .build();
	}
			
	@Bean		
	public  CircuitBreakerConfig circuitBreakerConfig() {
		return CircuitBreakerConfig.custom()
				  .failureRateThreshold(60)
				  .waitDurationInOpenState(Duration.ofMillis(5000))  // wait for 5s before breaking it
				  .slidingWindowSize(5)  // minimum number of calls to calculate
				  .build();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> customConfig(){
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(timeLimiterConfig())
				.circuitBreakerConfig(circuitBreakerConfig())
				.build());
	}
}
