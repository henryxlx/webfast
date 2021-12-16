package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteArticleController")
public class ArticleController {

    @RequestMapping("/article")
    public String indexPage() {
        return "/article/index";
    }
}
