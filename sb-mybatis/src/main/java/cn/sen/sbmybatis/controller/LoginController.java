package cn.sen.sbmybatis.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/login")
    public String login(@RequestParam(value = "name", required = false, defaultValue = "admin") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

}
