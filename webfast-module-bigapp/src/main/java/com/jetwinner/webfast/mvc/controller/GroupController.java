package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteGroupController")
public class GroupController {

    @RequestMapping("/group")
    public String indexPage() {
        return "/group/index";
    }

    @RequestMapping("/group/search-group")
    public String searchPage() {
        return "/group/search";
    }
}
