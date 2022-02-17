package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("webfastSiteSearchController")
public class SearchController {

    @RequestMapping("/search")
    public String indexPage() {
        return "/search/index";
    }
}
