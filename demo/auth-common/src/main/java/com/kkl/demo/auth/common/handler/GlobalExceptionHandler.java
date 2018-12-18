package com.kkl.demo.auth.common.handler;

import com.kkl.demo.auth.common.entity.MSErrorCode;
import com.kkl.demo.auth.common.entity.MSResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice("com.kkl.demo")
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public MSResponse ExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        log.error(ex.getMessage(), ex);
        return new MSResponse(MSErrorCode.newInstance(MSErrorCode.FAILURE, ex.getMessage()));
    }
}
