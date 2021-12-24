package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
