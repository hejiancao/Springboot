package com.example.demo.ex;


/**
 * 自定义异常
 * @author created by shaos on 2019/6/12
 */
public class AIWAYSException extends RuntimeException {

    private String code;

    public AIWAYSException() {}

    public AIWAYSException(String message) {
        super(message);
        this.code = "-1";
    }

    public AIWAYSException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
