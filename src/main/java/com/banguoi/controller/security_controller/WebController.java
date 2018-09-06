package com.banguoi.controller.security_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value={"/","home"})
    public String home(){
        return "/home";
    }

    @RequestMapping(value="/user")
    public String user(){
        return "/userPage";
    }

    @RequestMapping(value="/admin")
    public String admin(){
        return "/adminPage";
    }

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "/403";
    }
}