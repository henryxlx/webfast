package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("webfastSitePasswordResetController")
public class PasswordResetController {

    @GetMapping("/password/reset")
    public String indexPage() {
        return "/password-reset/index";
    }

}
