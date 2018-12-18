package com.kkl.demo.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.kkl.demo.auth.client.feign.AuthFeign;
import com.kkl.demo.auth.client.jwt.ServiceAuthUtil;
import com.kkl.demo.auth.client.jwt.UserAuthUtil;
import com.kkl.demo.auth.common.entity.IJWTInfo;
import com.kkl.demo.auth.common.entity.MSErrorCode;
import com.kkl.demo.auth.common.entity.MSResponse;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AccessFilter extends ZuulFilter {
    @Value("${jwt.client.client-id}")
    private String clientId;

    @Value("${jwt.client.client-secret}")
    private String clientSecret;

    @Autowired
    private AuthFeign authFeign;
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getRequestURI().contains("Token")){
            return null;
        }
        String token = request.getHeader("user-token");
        // 验证用户合法性
        IJWTInfo user = null;
        try {
            user = userAuthUtil.getInfoFromToken(token);
            log.info("** current user {} ***", user);
            // todo 用户权限
            String clientToken = authFeign.getClientToken(clientId, clientSecret);
            ctx.addZuulRequestHeader("client-token", clientToken);
            log.info("=== client token: {} ===", clientToken);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setResponseBody(JSON.toJSONString(new MSResponse<>(MSErrorCode.newInstance(MSErrorCode.FAILURE, "Token error or Token is Expired！"))));
        }

        return null;
    }
}
