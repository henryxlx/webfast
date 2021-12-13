package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jingjianxin
 */
@Controller("webfastSiteNotificationController")
public class NotificationController {

    @RequestMapping("/notification")
    public String indexPage(){
        return "/notification/index";
    }
}
