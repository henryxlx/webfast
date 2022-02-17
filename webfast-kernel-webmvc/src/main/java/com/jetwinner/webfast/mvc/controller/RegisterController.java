package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("webfastSiteRegisterController")
public class RegisterController {

    @GetMapping("/register")
    public String indexPage() {
        return "/register/index";
    }

}
