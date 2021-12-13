package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminRoleController")
public class RoleController {

    @GetMapping("/admin/role")
    public String indexPage() {
        return "/admin/role/index";
    }

    @GetMapping("/admin/role/create")
    public String createRolePage() {
        return "/admin/role/create-model";
    }
}
