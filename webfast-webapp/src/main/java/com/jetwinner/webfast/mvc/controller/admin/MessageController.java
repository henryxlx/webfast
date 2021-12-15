package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xulixin
 */
@Controller("adminMessageController")
public class MessageController {

    @GetMapping("/admin/message")
    public String indexPage() {
        return "/admin/message/index";
    }
}
