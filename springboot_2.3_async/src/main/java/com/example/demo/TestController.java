package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created by shaos on 2019/6/25
 */

@RestController
public class TestController {


    @Autowired
    private TestService testService;

    @GetMapping("/")
    public void contextLoads() {
        System.out.println("main--start");
        testService.async();
        System.out.println("main--end");
    }

}
