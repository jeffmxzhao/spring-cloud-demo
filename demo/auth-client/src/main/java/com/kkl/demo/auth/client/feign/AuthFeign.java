package com.kkl.demo.auth.client.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("${jwt.client.service-id}")
public interface AuthFeign {
    @PostMapping("/getClientToken")
    String getClientToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @PostMapping("/getAllowedList")
    List<String> getAllowedList(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @PostMapping("/getUserPublicKey")
    byte[] getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @PostMapping("/getClientPublicKey")
    byte[] getClientPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
}
