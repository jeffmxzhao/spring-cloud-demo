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
public class JwtUserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("jwt user inteceptor....");
        String userToken = request.getHeader("user-token");
        // 验证用户合法性MSResponse
        IJWTInfo user = userAuthUtil.getInfoFromToken(userToken);
        if (user != null) {
            log.info("** current user {} ***", user);
            return super.preHandle(request, response, handler);
        }
        // todo 用户权限
        throw new Exception("User Token error or Token is Expired！");
    }
}
