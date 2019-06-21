package com.example.demo.ex;

import com.example.demo.utils.RespResult;
import com.example.demo.utils.RespResultUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 * @author created by shaos on 2019/6/12
 */
@ControllerAdvice
public class ExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespResult handle(Exception e) {
        if (e instanceof AiwaysException) {
            AiwaysException ex = (AiwaysException) e;
            return RespResultUtils.returnJson(ex.getCode(), ex.getMessage(), e.getCause());
        } else {
            log.error("[系统异常] :" + ExceptionUtils.getStackTrace(e));
            return RespResultUtils.returnJson("-1", "error", e.toString());
            // test
        }
    }

}
