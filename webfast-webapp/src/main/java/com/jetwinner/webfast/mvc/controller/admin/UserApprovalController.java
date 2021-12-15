package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xulixin
 */
@Controller("adminUserApprovalController")
public class UserApprovalController {

    @GetMapping("/admin/approval/approving")
    public String approvingPage() {
        return "/admin/user/approving";
    }

    @GetMapping("/admin/approval/approved")
    public String approvedPage() {
        return "/admin/user/approved";
    }
}
