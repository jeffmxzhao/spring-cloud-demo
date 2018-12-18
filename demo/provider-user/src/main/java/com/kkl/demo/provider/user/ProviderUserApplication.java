package com.kkl.demo.provider.user;

import com.kkl.demo.auth.client.EnableAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.kkl.demo.auth.client.feign")
@EnableAuthClient
public class ProviderUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProviderUserApplication.class, args);
	}
}
