package com.kkl.demo.auth.server.controller;

import com.kkl.demo.auth.common.entity.IJWTInfo;
import com.kkl.demo.auth.server.config.KeyConfiguration;
import com.kkl.demo.auth.server.service.JwtClientService;
import com.kkl.demo.auth.server.service.JwtUserService;
import com.kkl.demo.auth.server.util.JwtClientTokenUtil;
import com.kkl.demo.auth.server.util.JwtUserTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class JwtController {

    @Autowired
    private JwtUserService jwtUserService;

    @Autowired
    private JwtClientService jwtClientService;

    @Autowired
    private JwtUserTokenUtil jwtUserTokenUtil;

    @Autowired
    private JwtClientTokenUtil jwtClientTokenUtil;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @PostMapping("/getUserToken")
    public String getUserToken(@RequestParam("name") String name, @RequestParam("password") String password){
        IJWTInfo user = jwtUserService.authUser(name, password);
        try {
            return jwtUserTokenUtil.generateToken(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
            return "user invalid";
        }
    }

    @PostMapping("/getClientToken")
    public String getClientToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret){
        IJWTInfo client = jwtClientService.authClient(clientId, secret);
        try {
            return jwtClientTokenUtil.generateToken(client);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
            return "client invalid";
        }
    }

    @PostMapping("/getAllowedList")
    public List<String> getAllowedList(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret){
        return jwtClientService.getAllowedList(clientId, secret);
    }

    @PostMapping("/getUserPublicKey")
    public byte[] getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        IJWTInfo client = jwtClientService.authClient(clientId, secret);
        if (client != null){
            return keyConfiguration.getUserPubKey();
        }
        throw new Exception("client is invalid.");
    }

    @PostMapping("/getClientPublicKey")
    public byte[] getClientPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        IJWTInfo client = jwtClientService.authClient(clientId, secret);
        if (client != null){
            return keyConfiguration.getClientPubKey();
        }
        throw new Exception("client is invalid.");
    }
}
