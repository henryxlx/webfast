package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.service.AppLogService;
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
    private final AppLogService logService;
    private final FastAppConst appConst;

    public SettingController(AppSettingService settingService, AppLogService logService, FastAppConst appConst) {
        this.settingService = settingService;
        this.logService = logService;
        this.appConst = appConst;
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
            siteMap = ParamMap.toFormDataMap(request);
            settingService.set("site", siteMap);
            logService.info(AppUser.getCurrentUser(request), "system", "update_settings",
                    "更新站点设置", siteMap);
            FlashMessageUtil.setFlashMessage("success", "站点信息设置已保存！", request.getSession());
        }
        model.addAttribute("site", siteMap);
        return "/admin/system/site";
    }

    @RequestMapping("/admin/setting/site/offline")
    public String siteOfflinePage(HttpServletRequest request, Model model) {
        if ("POST".equals(request.getMethod())) {
            String offline = request.getParameter("offline");
            appConst.setOffline("true".equalsIgnoreCase(offline) ? true : false);
            FlashMessageUtil.setFlashMessage("success",
                    String.format("站点当前是【%s】状态！", appConst.getOffline() ? "离线" : "在线"), request.getSession());
        }
        model.addAttribute("offline", appConst.getOffline());
        return "/admin/system/site-offline";
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
            mailer = ParamMap.toFormDataMap(request);
            settingService.set("mailer", mailer);
            logService.info(AppUser.getCurrentUser(request), "system", "update_settings",
                    "更新邮件服务器设置", mailer);
            FlashMessageUtil.setFlashMessage("success", "电子邮件设置已保存！", request.getSession());
        }

        model.addAttribute("mailer", mailer);
        return "/admin/system/mailer";
    }

    @RequestMapping("/admin/setting/auth")
    public String authPage(HttpServletRequest request, Model model) {
        Map<String, Object> auth = settingService.get("auth");

        Map<String, Object> defaultMap = new ParamMap()
                .add("register_mode", "closed")
                .add("email_enabled", "closed")
                .add("setting_time", -1)
                .add("email_activation_title", "")
                .add("email_activation_body", "")
                .add("welcome_enabled", "closed")
                .add("welcome_sender", "")
                .add("welcome_methods", new String[0])
                .add("welcome_title", "")
                .add("welcome_body", "")
                .add("user_terms", "closed")
                .add("user_terms_body", "")
                .add("registerFieldNameArray", new String[0])
                .add("registerSort", new ParamMap().add("0", "email").add("1", "nickname").add("2", "password").toMap())
                .add("captcha_enabled", 0)
                .add("register_protective", "none").toMap();

        if (EasyStringUtil.isNotBlank(auth.get("captcha_enabled"))) {
            if (EasyStringUtil.isBlank(auth.get("register_protective"))) {
                auth.put("register_protective", "low");
            }

        }

        defaultMap.putAll(auth);
        auth = defaultMap;
        if ("POST".equals(request.getMethod())) {
            if (EasyStringUtil.isNotBlank(auth.get("setting_time")) && ValueParser.parseLong(auth.get("setting_time")) > 0) {
                Object firstSettingTime = auth.get("setting_time");
                auth = ParamMap.toCustomFormDataMap(request, auth.keySet().toArray(new String[auth.keySet().size()]));
                auth.put("setting_time", firstSettingTime);
            } else {
                auth = ParamMap.toUpdateDataMap(request, auth);
                auth.put("setting_time", System.currentTimeMillis());
            }

            if (!auth.containsKey("welcome_methods")) {
                auth.put("welcome_methods", new String[0]);
            }

            if ("none".equals(auth.get("register_protective"))) {
                auth.put("captcha_enabled", 0);
            } else {
                auth.put("captcha_enabled", 1);
            }

            settingService.set("auth", auth);

            logService.info(AppUser.getCurrentUser(request), "system", "update_settings",
                    "更新注册设置", auth);
            FlashMessageUtil.setFlashMessage("success", "注册设置已保存！", request.getSession());
        }

        model.addAttribute("auth", auth);
        return "/admin/system/auth";
    }

    @RequestMapping("/admin/setting/login-connect")
    public String loginConnectAction(HttpServletRequest request, Model model) {
        Map<String, Object> loginConnect = settingService.get("login_bind");

        Map<String, Object> defaultMap = new ParamMap()
                .add("login_limit", 0)
                .add("enabled", 0)
                .add("verify_code", "")
                .add("captcha_enabled", 0)
                .add("temporary_lock_enabled", 0)
                .add("temporary_lock_allowed_times", 5)
                .add("temporary_lock_minutes", 20).toMap();

        /* Map<String, Object> clients = OAuthClientFactory.clients();
        clients.forEach((key, value) -> {
            defaultMap.put(key + "_enabled", 0);
            defaultMap.put(key + "_key", "");
            defaultMap.put(key + "_secret", "");
            defaultMap.put(key + "_set_fill_account", 0);
        });
        model.addAttribute("clients", clients); */

        defaultMap.putAll(loginConnect);
        loginConnect = defaultMap;
        if ("POST".equals(request.getMethod())) {
            loginConnect = ParamMap.toFormDataMap(request);
            settingService.set("login_bind", loginConnect);
            logService.info(AppUser.getCurrentUser(request), "system", "update_settings", "更新登录设置", loginConnect);
            FlashMessageUtil.setFlashMessage("success", "登录设置已保存！", request.getSession());
        }

        model.addAttribute("loginConnect", loginConnect);
        return "/admin/system/login-connect";
    }
}
