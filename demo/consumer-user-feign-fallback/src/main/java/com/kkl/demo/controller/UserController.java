package com.kkl.demo.controller;

import com.kkl.demo.entity.md.User;
import com.kkl.demo.feigns.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserFeign userFeign;

    @GetMapping("/user/getUserInfo/{id}")
    public User getUserInfo(@PathVariable Long id){
        return userFeign.getUserInfo(id);
    }


    @GetMapping("/user/getHelloMessage/{id}")
    public String getHelloMessage(@PathVariable Long id){
        return userFeign.getHelloMessage(id);
    }

}
