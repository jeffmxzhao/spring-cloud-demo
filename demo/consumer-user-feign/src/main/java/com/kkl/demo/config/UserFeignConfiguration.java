package com.kkl.demo.config;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ExcludeFromComponentScan
public class UserFeignConfiguration {
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("test", "test");
//    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
