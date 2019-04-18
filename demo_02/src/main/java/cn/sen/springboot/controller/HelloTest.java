package cn.sen.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTest {


    /**
     * 添加加载器实现热部署
     * https://blog.csdn.net/hz_940611/article/details/80594788
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        return "success";
    }
}
