package com.kkl.demo.auth.server.util;

import com.kkl.demo.auth.common.entity.IJWTInfo;
import com.kkl.demo.auth.common.jwt.JWTHelper;
import com.kkl.demo.auth.server.config.KeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class JwtClientTokenUtil {

    @Value("${jwt.client.rsa-expire}")
    private int expire;

    @Autowired
    private KeyConfiguration keyConfiguration;

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, keyConfiguration.getClientPriKey(), expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token, keyConfiguration.getClientPubKey());
    }


}
