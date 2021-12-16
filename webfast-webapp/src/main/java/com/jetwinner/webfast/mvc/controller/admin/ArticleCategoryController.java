package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xulixin
 */
@Controller("adminArticleCategoryController")
public class ArticleCategoryController {

    @GetMapping("/admin/article/category")
    public String indexAction() {
        return "/admin/article/category/index";
    }
}
