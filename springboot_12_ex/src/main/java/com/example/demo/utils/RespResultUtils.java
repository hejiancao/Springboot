package com.example.demo.utils;

import java.util.Objects;

/**
 * @author created by shaos on 2019/6/21
 */
public class RespResultUtils {
    /**
     * 返回code,msg,data格式的数据给前端
     *
     * @param code
     * @param msg
     * @param data
     * @author shaos
     * @date 2019/6/12 13:58
     */
    public static RespResult returnJson(String code, String msg, Object data) {
        RespResult<Object> resultDto = new RespResult<>();
        resultDto.setCode(code);
        resultDto.setMsg(msg);
        resultDto.setData(data);
        return resultDto;
    }

    /**
     * 返回成功不带参数
     *
     * @author shaos
     * @date 2019/6/13 9:48
     */
    public static RespResult success() {
        return success(null);
    }

    /**
     * 返回成功带参数
     *
     * @param data
     * @author shaos
     * @date 2019/6/13 9:49
     */
    public static RespResult success(Object data) {
        return returnJson("200", "success", data);
    }

    /**
     * @param data
     * @author shaos
     * @date 2019/6/21 12:01
     */
    public static RespResult failure(Object data) {
        return RespResultUtils.returnJson("-1", "error", data);
    }
}
