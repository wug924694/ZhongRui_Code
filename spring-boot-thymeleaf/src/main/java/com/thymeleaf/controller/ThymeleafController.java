package com.thymeleaf.controller;

import com.thymeleaf.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/test")
public class ThymeleafController {

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("message","hello world");

        ArrayList<User> list = new ArrayList<>();
        list.add(new User("张三","18","男"));
        list.add(new User("李四","18","女"));
        list.add(new User("王五","18","男"));

        model.addAttribute("user",list);
        return "index";
    }
}
