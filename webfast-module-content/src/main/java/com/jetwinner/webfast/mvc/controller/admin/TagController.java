package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller
public class TagController {

    @RequestMapping("/admin/tag")
    public String indexPage() {
        return "/admin/tag/index";
    }
}
