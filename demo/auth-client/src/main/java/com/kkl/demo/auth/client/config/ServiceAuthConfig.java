package com.kkl.demo.auth.client.config;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ace on 2017/9/15.
 */

public class ServiceAuthConfig {
    private byte[] pubKeyByte;
    @Value("${jwt.client.client-id:null}")
    private String clientId;
    @Value("${jwt.client.client-secret}")
    private String clientSecret;
    @Value("${spring.application.name}")
    private String applicationName;

    public String getTokenHeader() {
        return "client-token";
    }


    public String getClientId() {
        return "null".equals(clientId)?applicationName:clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getToken(HttpServletRequest request){
        return request.getHeader(this.getTokenHeader());
    }

    public byte[] getPubKeyByte() {
        return pubKeyByte;
    }

    public void setPubKeyByte(byte[] pubKeyByte) {
        this.pubKeyByte = pubKeyByte;
    }
}
