package com.kkl.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author ace
 */
@Component
@Slf4j
public class OkHttpTokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request newRequest = null;
//        if (chain.request().url().toString().contains("client/token")) {
//            newRequest = chain.request()
//                    .newBuilder()
//                    .header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
//                    .build();
//        } else {
//            newRequest = chain.request()
//                    .newBuilder()
//                    .header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
//                    .header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken())
//                    .build();
//        }
//        Response response = chain.proceed(newRequest);
//        if (HttpStatus.FORBIDDEN.value() == response.code()) {
//            if (response.body().string().contains(String.valueOf(CommonConstants.EX_CLIENT_INVALID_CODE))) {
//                log.info("Client Token Expire,Retry to request...");
//                serviceAuthUtil.refreshClientToken();
//                newRequest = chain.request()
//                        .newBuilder()
//                        .header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
//                        .header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken())
//                        .build();
//                response = chain.proceed(newRequest);
//            }
//        }
        Request newRequest = chain.request().newBuilder()
                    .header("service-token", "test-service-token")
                    .build();
        log.debug("*** OKHttpTokenInterceptor.intercept, set token to header, {}", newRequest.url().toString());
        return chain.proceed(newRequest);
    }
}
