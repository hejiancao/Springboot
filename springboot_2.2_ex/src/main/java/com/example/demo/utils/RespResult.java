package com.example.demo.utils;

/**
 * @author created by shaos on 2019/6/21
 */
public class RespResult<T> {

    private String code;
    private String msg;
    private T data;

    public RespResult() {
    }

    public RespResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
