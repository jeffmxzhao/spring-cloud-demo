package com.kkl.demo.service;

import com.kkl.demo.entity.md.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUserInfo(Long id){
        return new User(id, "swift", 18, "male", null);
    }

    public String getHelloMessage(Long id){
        return "Hello,Swift";
    }
}
