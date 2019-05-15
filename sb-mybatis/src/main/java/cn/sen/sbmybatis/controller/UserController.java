package cn.sen.sbmybatis.controller;

import cn.sen.sbmybatis.model.User;
import cn.sen.sbmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "users/list")
    public List<User> listUser() {
        List<User> list = userService.listUser();
        return list;
    }

}
