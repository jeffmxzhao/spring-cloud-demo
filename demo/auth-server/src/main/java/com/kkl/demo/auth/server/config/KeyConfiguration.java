package com.kkl.demo.auth.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ace
 * @create 2017/12/17.
 */
@Configuration
@Data
public class KeyConfiguration {
    @Value("${jwt.user.rsa-secret}")
    private String userSecret;
    @Value("${jwt.client.rsa-secret}")
    private String clientSecret;
    private byte[] userPubKey;
    private byte[] userPriKey;
    private byte[] clientPriKey;
    private byte[] clientPubKey;
}
