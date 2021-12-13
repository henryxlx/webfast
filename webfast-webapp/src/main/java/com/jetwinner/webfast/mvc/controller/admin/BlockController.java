package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminBlockController")
public class BlockController {

    @RequestMapping("/admin/block")
    public String indexPage() {
        return "/admin/block/index";
    }
}
