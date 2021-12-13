package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteDefaultController")
public class DefaultController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "/default/index";
    }
}
