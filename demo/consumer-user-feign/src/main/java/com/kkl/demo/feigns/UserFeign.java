package com.kkl.demo.feigns;

import com.kkl.demo.config.UserFeignConfiguration;
import com.kkl.demo.entity.md.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(name = "provider-user")
@FeignClient(name = "provider-user", configuration = UserFeignConfiguration.class)
public interface UserFeign {
//    @RequestMapping( value = "/user/getUserInfo/{id}", method = RequestMethod.GET)
//    User getUserInfo(@PathVariable(value = "id") Long id);
//
//    @RequestMapping( value = "/user/getHelloMessage/{id}", method = RequestMethod.GET)
//    String getHelloMessage(@PathVariable(value = "id") Long id);

    @RequestLine( value = "GET /user/getUserInfo/{id}")
    User getUserInfo(@Param(value = "id") Long id);

    @RequestLine( value = "GET /user/getHelloMessage/{id}")
    String getHelloMessage(@Param(value = "id") Long id);

    @RequestLine( value = "POST /user/postUserInfo")
    User postUserInfo(@RequestBody User user);
}
