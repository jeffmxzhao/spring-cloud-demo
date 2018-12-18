package com.kkl.demo.auth.server.util;

import com.kkl.demo.auth.common.jwt.RsaKeyHelper;
import com.kkl.demo.auth.server.config.KeyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author ace
 * @create 2017/12/17.
 */
@Configuration
public class AuthServerRunner implements CommandLineRunner {

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Override
    public void run(String... args) throws Exception {
        Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(keyConfiguration.getUserSecret());
        keyConfiguration.setUserPriKey(keyMap.get("pri"));
        keyConfiguration.setUserPubKey(keyMap.get("pub"));

        keyMap = RsaKeyHelper.generateKey(keyConfiguration.getClientSecret());
        keyConfiguration.setClientPriKey(keyMap.get("pri"));
        keyConfiguration.setClientPubKey(keyMap.get("pub"));
    }
}