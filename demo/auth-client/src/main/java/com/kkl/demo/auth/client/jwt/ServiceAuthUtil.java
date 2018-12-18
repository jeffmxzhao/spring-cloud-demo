
package com.kkl.demo.auth.client.jwt;

import com.kkl.demo.auth.client.config.ServiceAuthConfig;
import com.kkl.demo.auth.client.feign.AuthFeign;
import com.kkl.demo.auth.common.entity.IJWTInfo;
import com.kkl.demo.auth.common.jwt.JWTHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
@Slf4j
@EnableScheduling
public class ServiceAuthUtil{
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private AuthFeign authFeign;

    private List<String> allowedClient;
    private String clientToken;


    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyByte());
        } catch (ExpiredJwtException ex) {
            throw new Exception("Client token expired!");
        } catch (SignatureException ex) {
            throw new Exception("Client token signature error!");
        } catch (IllegalArgumentException ex) {
            throw new Exception("Client token is null or empty!");
        }
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void refreshAllowedClient() {
        log.debug("refresh allowedClient.....");
        this.allowedClient = authFeign.getAllowedList(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.debug("refresh client token.....");
        String clientToken = authFeign.getClientToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        this.clientToken = clientToken;
    }


    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    public List<String> getAllowedClient() {
        if (this.allowedClient == null) {
            this.refreshAllowedClient();
        }
        return allowedClient;
    }
}