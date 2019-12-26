package com.example.demo.controller;

import com.example.demo.entity.PersonForm;
import com.example.demo.utils.RespResult;
import com.example.demo.utils.RespResultUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author created by shaos on 2019/6/21
 */

@RestController
public class PersonController {


    /** 验证表单，并用统一异常处理
     * @param personForm
     * @param bindingResult
     * @author shaos
     * @date 2019/6/21 12:05
     */
    @PostMapping("/")
    public RespResult testEx(@RequestBody @Valid PersonForm personForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                return RespResultUtils.failure(objectError.toString());
//                throw new AiwaysException(objectError.getDefaultMessage());
            }

        }
        return RespResultUtils.success();
    }


}
