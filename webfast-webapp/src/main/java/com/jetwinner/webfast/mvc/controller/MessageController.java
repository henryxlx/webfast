package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author libingquan
 */
@Controller("webfastSiteMessageController")
public class MessageController {

    @RequestMapping("/message")
    public String indexPage(){
        return "/message/index";
    }
}
