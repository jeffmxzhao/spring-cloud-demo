package com.kkl.demo.controller;

import com.kkl.demo.entity.md.User;
import com.kkl.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController{
    @Value("${spring.profiles}")
    private String springProfiles;

    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo/{id}")
    public User getUserInfo(@PathVariable Long id){
        log.info("====== " + springProfiles + " getUserInfo ======");
        User user = userService.getUserInfo(id);
        user.setTestDate(new Date());
        return user;
    }

    @GetMapping("/getHelloMessage/{id}")
    public String getHelloMessage(@PathVariable Long id){
        log.info("====== " + springProfiles + " getHelloMessage ======");
        return userService.getHelloMessage(id);
    }

    @PostMapping("/postUserInfo")
    public User postUserInfo(@RequestBody User user){
        User returnUser = userService.getUserInfo(user.getId());
        returnUser.setTestDate(new Date());
        return returnUser;
    }
}
