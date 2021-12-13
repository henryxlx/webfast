package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminContentController")
public class ContentController {

    @RequestMapping("/admin/content")
    public String indexPage() {
        return "/admin/content/index";
    }
}
