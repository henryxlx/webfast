package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminSettingController")
public class SettingController {

    private final AppSettingService settingService;

    public SettingController(AppSettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/admin/setting/site")
    public String sitePage(Model model) {
        model.addAttribute("site", settingService.get("site"));
        return "/admin/system/site";
    }

    @RequestMapping("/admin/setting/default")
    public String defaultPage() {
        return "redirect:/admin/setting/mailer";
    }

    @GetMapping("/admin/setting/mailer")
    public String mailerPage(Model model) {
        model.addAttribute("mailer", settingService.get("mailer"));
        return "/admin/system/mailer";
    }
}
