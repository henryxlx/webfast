package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminPermissionController")
public class PermissionController {

    @GetMapping("/admin/permission")
    public String indexPage() {
        return "/admin/permission/index";
    }

    @GetMapping("/admin/permission/create")
    public String createRolePage() {
        return "/admin/permission/create-model";
    }
}
