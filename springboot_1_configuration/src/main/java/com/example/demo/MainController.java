package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created by shaos on 2019/6/18
 */
@RestController
public class MainController {

    @Value("${student.name}")
    String name;
    @Value("${student.age}")
    String age;

    @Autowired
    private Student student;
    @Autowired
    private User user;

    @GetMapping("/hi")
    public String hi() {
        return "name:" + name + ",age:" + age;
    }

    @GetMapping("/info")
    public String info() {
        return student.toString();
    }

    @GetMapping("/user")
    public String userInfo() {
        return user.toString();
    }
}
