package ru.zheleznov.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public String index(){
        return "index";
    }

    @GetMapping(value="/registration")
    public String register(){
        return "register";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping(value="/talkPage/{id}")
    public String talk(@PathVariable Long id){
        return "talk";
    }

    @GetMapping(value="talk/create")
    public String createTalk() { return "create_talk"; }

    @GetMapping(value="/admin")
    public String adminPage() {
        return "admin";
    }
}
