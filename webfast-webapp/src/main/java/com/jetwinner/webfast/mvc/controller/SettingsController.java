package com.jetwinner.webfast.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteSettingController")
@RequestMapping(SettingsController.VIEW_PATH)
public class SettingsController {

    static final String VIEW_PATH ="/settings";

    @RequestMapping("")
    public String profilePage() {
        return VIEW_PATH + "/profile";
    }

    @RequestMapping("/avatar")
    public String avatarPage() {
        return VIEW_PATH + "/avatar";
    }

    @RequestMapping("/security")
    public String securityPage() {
        return VIEW_PATH + "/security";
    }

    @RequestMapping("/email")
    public String emailPage() {
        return VIEW_PATH + "/email";
    }

    @RequestMapping("/setup")
    public String setupPage() {
        return VIEW_PATH + "/setup";
    }
}
