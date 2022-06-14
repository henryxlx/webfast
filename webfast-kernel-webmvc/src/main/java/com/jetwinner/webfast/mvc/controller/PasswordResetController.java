package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.servlet.RequestContextPathUtil;
import com.jetwinner.webfast.email.FastEmailService;
import com.jetwinner.webfast.email.FastEmailServiceImpl;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.kernel.view.ViewRenderService;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller("webfastSitePasswordResetController")
public class PasswordResetController {

    private final AppUserService userService;
    private final AppLogService logService;
    private final AppSettingService settingService;
    private final ViewRenderService viewRenderService;
    private final FastEmailService emailService;

    public PasswordResetController(AppUserService userService, AppLogService logService,
                                   AppSettingService settingService,
                                   ViewRenderService viewRenderService,
                                   FastEmailService emailService) {

        this.userService = userService;
        this.logService = logService;
        this.settingService = settingService;
        this.viewRenderService = viewRenderService;
        this.emailService = emailService;
    }

    @GetMapping("/password/reset")
    public String indexPage() {
        return "/password-reset/index";
    }

    @PostMapping("/password/reset")
    public ModelAndView indexAction(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/password-reset/index");
        String errorMessage = null;

        Map<String, Object> data = ParamMap.toFormDataMap(request);
        AppUser user = userService.getUserByEmail(String.valueOf(data.get("email")));

        if (user == null) {
            boolean result = userService.existEmail(String.valueOf(data.get("email")));
            if (!result) {
                mav.addObject("errorMessage", "请通过论坛找回密码");
                return mav;
            }
        }

        if (user != null) {
            String token = userService.makeToken("password-reset", user.getId(),
                    System.currentTimeMillis() + (24 * 60 * 60 * 1000));

            try {
                String siteName = settingService.getSettingValue("site.name", "WEBFAST");
                emailService.sendEmail(user.getEmail(),
                        String.format("重设%s在%s的密码", user.getUsername(), siteName),

                        viewRenderService.renderView("/password-reset/reset",
                                new ParamMap().add("user", user).add("token", token).add("siteName", siteName)
                                        .add("baseUrl", RequestContextPathUtil.createBaseUrl(request))
                                        .add("siteUrl", settingService.getSettingValue("site.url")).toMap()),
                        FastEmailService.MailTypeEnum.HtmlMail);
            } catch (Exception e) {
                logService.error(user, "user", "password-reset",
                        "重设密码邮件发送失败:" + e.getMessage());
                return BaseControllerHelper.createMessageResponse("error", "重设密码邮件发送失败，请联系管理员。");
            }

            logService.info(user, "user", "password-reset",
                    String.format("向%s发送了找回密码邮件。", user.getEmail()));

            mav.addObject("user", user);
            mav.addObject("emailLoginUrl", this.getEmailLoginUrl(user.getEmail()));
            mav.setViewName("/password-reset/sent");
            return mav;
        } else {
            errorMessage = "该邮箱地址没有注册过帐号";
        }

        if (errorMessage != null) {
            mav.addObject("errorMessage", errorMessage);
        }
        return mav;
    }

    public String getEmailLoginUrl(String email) {
        String host = email.substring(email.indexOf('@') + 1);
        if ("hotmail.com".equals(host)) {
            return "http://www." + host;
        }
        if ("gmail.com".equals(host)) {
            return "http://mail.google.com";
        }
        return "http://mail."  + host;
    }

}
