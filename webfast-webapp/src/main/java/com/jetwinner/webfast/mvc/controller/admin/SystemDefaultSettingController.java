package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminSystemDefaultSettingController")
public class SystemDefaultSettingController {

    @RequestMapping("/admin/setting/site")
    public String sitePage() {
        return "/admin/system/site";
    }

    @RequestMapping("/admin/setting/default")
    public String defaultPage() {
        return "/admin/system/default";
    }

    @RequestMapping("/admin/setting/mailer")
    public String mailerPage() {
        return "/admin/system/mailer";
    }

    @RequestMapping("/admin/setting/theme")
    public String themePage() {
        return "/admin/system/theme";
    }

    @RequestMapping("/admin/setting/auth")
    public String authPage() {
        return "/admin/system/auth";
    }

    @RequestMapping("/admin/setting/login-connect")
    public String loginConnectPage() {
        return "/admin/system/login-connect";
    }

    @RequestMapping("/admin/setting/user-center")
    public String userCenterPage() {
        return "/admin/system/user-center";
    }

    @RequestMapping("/admin/setting/admin-sync")
    public String adminSyncPage() {
        return "/admin/system/admin-sync";
    }

    @RequestMapping("/admin/setting/user-fields")
    public String userFieldsPage() {
        return "/admin/system/user-fields";
    }

    @RequestMapping("/admin/setting/mobile")
    public String mobilePage(Model model) {
        return "/admin/system/mobile";
    }

    @RequestMapping("/admin/setting/ip-blacklist")
    public String ipBlackListPage() {
        return "/admin/system/ip-blacklist";
    }

    @RequestMapping("/admin/optimize")
    public String optimizePage() {
        return "/admin/system/optimize";
    }

    @RequestMapping("/admin/logs")
    public String logsPage() {
        return "/admin/system/logs";
    }

}
