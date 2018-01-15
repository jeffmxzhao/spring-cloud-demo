package com.kkl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderDictMybatisRedisApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProviderDictMybatisRedisApplication.class, args);
	}
}
