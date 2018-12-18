package com.kkl.demo.feigns;

import com.kkl.demo.entity.md.User;
import com.kkl.demo.fallback.UserFeignFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "zuul-gateway", fallback = UserFeignFallback.class)
public interface UserFeign {
    @RequestMapping( value = "/user-api/getUserInfo/{id}", method = RequestMethod.GET)
    User getUserInfo(@PathVariable(value = "id") Long id);

    @RequestMapping( value = "/user-api/getHelloMessage/{id}", method = RequestMethod.GET)
    String getHelloMessage(@PathVariable(value = "id") Long id);

}
