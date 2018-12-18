package com.kkl.demo.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.debug("*** ClientTokenInterceptor.apply, set token to header, {}", requestTemplate.url());
        requestTemplate.header("service-token","test-service-token");
    }
}
