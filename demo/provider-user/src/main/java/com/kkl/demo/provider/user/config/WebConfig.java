package com.kkl.demo.provider.user.config;

import com.kkl.demo.provider.user.interceptor.JwtClientInterceptor;
import com.kkl.demo.provider.user.interceptor.JwtUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private JwtClientInterceptor jwtClientInterceptor;
    @Autowired
    private JwtUserInterceptor jwtUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtClientInterceptor).addPathPatterns("/**");
        registry.addInterceptor(jwtUserInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
