package com.example.demo.ex;


/**
 * 自定义异常
 * @author created by shaos on 2019/6/12
 */
public class AiwaysException extends RuntimeException {

    private String code;

    public AiwaysException() {}

    public AiwaysException(String message) {
        super(message);
        this.code = "-1";
    }

    public AiwaysException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
