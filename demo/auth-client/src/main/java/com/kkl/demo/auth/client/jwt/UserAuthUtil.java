package com.kkl.demo.auth.client.jwt;

import com.kkl.demo.auth.client.config.UserAuthConfig;
import com.kkl.demo.auth.common.entity.IJWTInfo;
import com.kkl.demo.auth.common.jwt.JWTHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
public class UserAuthUtil {
    @Autowired
    private UserAuthConfig userAuthConfig;
    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
        }catch (ExpiredJwtException ex){
            throw new Exception("User token expired!");
        }catch (SignatureException ex){
            throw new Exception("User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new Exception("User token is null or empty!");
        }
    }
}
