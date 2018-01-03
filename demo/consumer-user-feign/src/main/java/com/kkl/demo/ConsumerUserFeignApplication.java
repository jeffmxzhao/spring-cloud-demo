package com.kkl.demo;

import com.kkl.demo.config.ExcludeFromComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@ComponentScan(excludeFilters={@ComponentScan.Filter(type= FilterType.ANNOTATION, value=ExcludeFromComponentScan.class)})
public class ConsumerUserFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerUserFeignApplication.class, args);
	}
}
