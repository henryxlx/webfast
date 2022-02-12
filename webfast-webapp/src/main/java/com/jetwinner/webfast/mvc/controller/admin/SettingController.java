package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.session.FlashMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminSettingController")
public class SettingController {

    private final AppSettingService settingService;

    public SettingController(AppSettingService settingService) {
        this.settingService = settingService;
    }

    @RequestMapping("/admin/setting/site")
    public String sitePage(HttpServletRequest request, Model model) {
        Map<String, Object> siteMapFromSaved = settingService.get("site");
        Map<String, Object> siteMap = new ParamMap()
                .add("name", "")
                .add("slogan", "")
                .add("url", "")
                .add("logo", "")
                .add("seo_keywords", "")
                .add("seo_description", "")
                .add("master_email", "")
                .add("icp", "")
                .add("analytics", "")
                .add("status", "open")
                .add("closed_note", "")
                .add("favicon", "")
                .add("copyright", "").toMap();
        siteMap.putAll(siteMapFromSaved);

        if ("POST".equals(request.getMethod())) {
            siteMap = ParamMap.toPostDataMap(request);
            settingService.set("site", siteMap);
            // logService.info(AppUser.getCurrentUser(request), "system", "update_settings", "更新站点设置", siteMap);
            FlashMessageUtil.setFlashMessage("success", "站点信息设置已保存！", request.getSession());
        }
        model.addAttribute("site", siteMap);
        return "/admin/system/site";
    }

    @RequestMapping("/admin/setting/default")
    public String defaultPage() {
        return "redirect:/admin/setting/mailer";
    }

    @RequestMapping("/admin/setting/mailer")
    public String mailerPage(HttpServletRequest request, Model model) {
        Map<String, Object> mailerMapFromSaved = settingService.get("mailer");
        Map<String, Object> mailer = new ParamMap()
                .add("enabled", 0)
                .add("host", "")
                .add("port", "")
                .add("username", "")
                .add("password", "")
                .add("from", "")
                .add("name", "").toMap();
        mailer.putAll(mailerMapFromSaved);

        if ("POST".equals(request.getMethod())) {
            mailer = ParamMap.toPostDataMap(request);
            settingService.set("mailer", mailer);
            // logService.info("system", "update_settings", "更新邮件服务器设置", mailer);
            FlashMessageUtil.setFlashMessage("success", "电子邮件设置已保存！", request.getSession());
        }

        model.addAttribute("mailer", mailer);
        return "/admin/system/mailer";
    }
}
