package com.kkl.demo.controller;

import com.kkl.demo.entity.md.User;
import com.kkl.demo.feigns.UserFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserFeign userFeign;

    @GetMapping("/user/getUserInfo/{id}")
    @HystrixCommand(fallbackMethod = "getUserInfoFallback")
    public User getUserInfo(@PathVariable Long id){
        return userFeign.getUserInfo(id);
    }

    public User getUserInfoFallback(Long id){
        return new User();
    }

    @GetMapping("/user/getHelloMessage/{id}")
    @HystrixCommand(fallbackMethod = "getHelloMessageFallback")
    public String getHelloMessage(@PathVariable Long id){
        return userFeign.getHelloMessage(id);
    }

    public String getHelloMessageFallback(Long id){
        return "Hello!";
    }
}
