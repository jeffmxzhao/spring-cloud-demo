package com.kkl.demo.controller;

import com.kkl.demo.entity.md.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/user")
public class UserController{
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getUserInfo/{id}")
    @HystrixCommand(fallbackMethod = "getUserInfoFallback")
    public User getUserInfo(@PathVariable Long id){
        return restTemplate.getForObject("http://provider-user/user/getUserInfo/" + id, User.class);
    }

    public User getUserInfoFallback(Long id){
        return new User();
    }

    @GetMapping("/getHelloMessage/{id}")
    @HystrixCommand(fallbackMethod = "getHelloMessageFallback")
    public String getHelloMessage(@PathVariable Long id){
        return restTemplate.getForObject("http://provider-user/user/getHelloMessage/" + id, String.class);
    }

    public String getHelloMessageFallback(Long id){
        return "Hello!";
    }
}
