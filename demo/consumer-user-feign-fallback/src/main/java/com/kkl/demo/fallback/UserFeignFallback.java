package com.kkl.demo.fallback;

import com.kkl.demo.entity.md.User;
import com.kkl.demo.feigns.UserFeign;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallback implements UserFeign {
    @Override
    public User getUserInfo(Long id) {
        return new User();
    }

    @Override
    public String getHelloMessage(Long id) {
        return "Fallback: hello!";
    }
}
