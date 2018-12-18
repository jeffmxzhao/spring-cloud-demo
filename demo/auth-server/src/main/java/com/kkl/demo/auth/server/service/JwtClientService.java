package com.kkl.demo.auth.server.service;

import com.google.common.collect.Lists;
import com.kkl.demo.auth.common.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JwtClientService {

    public Client authClient(String clientId, String secret){
        try {
            if (clientId.equals("zuul-gateway") && secret.equals("123456")){
                return new Client(1L, clientId);
            }
            if (clientId.equals("provider-user") && secret.equals("123456")){
                return new Client(2L, clientId);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getAllowedList(String clientId, String secret){
        List<String> allowedList = Lists.newArrayList();
        if (clientId.equals("provider-user") && secret.equals("123456")){
            allowedList.add("zuul-gateway");
        }
        return allowedList;
    }
}
