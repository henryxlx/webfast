package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xulixin
 */
@Controller("adminLoginRecordController")
public class LoginRecordController {

    @GetMapping("/admin/login-record")
    public String indexPage() {
        return "/admin/login-record/index";
    }
}
