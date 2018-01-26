package com.kkl.demo;

import com.kkl.demo.config.GsonRedisSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ProviderDictMybatisRedis2Application {

	@Bean(name="gsonRedisSerializer")
	public GsonRedisSerializer gsonRedisSerializer() {
		GsonRedisSerializer<Object> gsonRedisSerializer = new GsonRedisSerializer(Object.class);
		return gsonRedisSerializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProviderDictMybatisRedis2Application.class, args);
	}
}
