package com.kkl.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ace on 2017/9/12.
 */
@SuppressWarnings("ALL")
@Slf4j
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("service-token");
        log.debug("*** ServiceAuthRestInterceptor.preHandle, {}, {}", "get and valid token", token);
//        if (token.equals("test-service-token")){
            return super.preHandle(request, response, handler);
//        }
//        throw new Exception("Client is Forbidden!");
    }
}
