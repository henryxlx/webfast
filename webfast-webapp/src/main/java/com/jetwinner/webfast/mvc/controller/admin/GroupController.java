package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jingjianxin
 */
@Controller("adminGroupController")
public class GroupController {

    @RequestMapping("/admin/group")
    public String indexPage() {
        return "/admin/group/index";
    }
}
