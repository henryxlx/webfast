package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminAppController")
public class AdminAppController {

    @RequestMapping({"/admin/app", "/admin/app/installed"})
    public String appInstalledPage() {
        return "/admin/app/installed";
    }
}
