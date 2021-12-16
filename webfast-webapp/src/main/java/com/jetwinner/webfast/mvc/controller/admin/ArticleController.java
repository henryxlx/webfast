package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("adminArticleController")
public class ArticleController {

    @GetMapping("/admin/article")
    public String indexPage() {
        return "/admin/article/index";
    }

    @RequestMapping("/admin/article/setting")
    public String settingPage() {
        return "/admin/article/setting";
    }
}
