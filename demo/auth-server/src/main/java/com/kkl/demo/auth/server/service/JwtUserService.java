package com.kkl.demo.auth.server.service;

import com.kkl.demo.auth.common.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserService {

    public User authUser(String name, String pwd){
        try {
            if (name.equals(pwd) && name.equals("admin")){
                return new User(1L, "admin");
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
