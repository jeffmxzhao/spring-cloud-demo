package com.kkl.demo.provider.user.interceptor;

import com.kkl.demo.auth.client.feign.AuthFeign;
import com.kkl.demo.auth.client.jwt.ServiceAuthUtil;
import com.kkl.demo.auth.client.jwt.UserAuthUtil;
import com.kkl.demo.auth.common.entity.IJWTInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Configuration
public class JwtClientInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.client.client-id}")
    private String clientId;
    @Value("${jwt.client.client-secret}")
    private String clientSecret;

    @Autowired
    private AuthFeign authFeign;
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("jwt client inteceptor....");
        String clientToken = request.getHeader("client-token");
        IJWTInfo client = serviceAuthUtil.getInfoFromToken(clientToken);
        log.info("** current client {} ***", client);
        List<String> allowedList = authFeign.getAllowedList(clientId, clientSecret);
        if (allowedList != null && allowedList.size() > 0){
            for (String cId : allowedList){
                if (client.getName().equals(cId)){
                    return super.preHandle(request, response, handler);
                }
            }
        }
        throw new Exception("Client Token error or Token is Expired！");
    }
}
