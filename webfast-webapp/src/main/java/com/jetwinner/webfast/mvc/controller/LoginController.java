package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.servlet.RequestIpAddressUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.service.AppLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
@Controller("webfastSiteLoginController")
public class LoginController {

    private static final String TARGET_PATH_KEY = "_target_path";

    private final UserAccessControlService userAccessControlService;
    private final AppLogService logService;

    public LoginController(UserAccessControlService userAccessControlService, AppLogService logService) {
        this.userAccessControlService = userAccessControlService;
        this.logService = logService;
    }

    @GetMapping("/login")
    public String indexPage(@RequestParam(value = TARGET_PATH_KEY, defaultValue = "") String targetPath, Model model) {
        model.addAttribute(TARGET_PATH_KEY, targetPath);
        return "/login/index";
    }

    @PostMapping("/login")
    public String doLoginAction(@RequestParam("_username") String username,
                                @RequestParam("_password") String password,
                                @RequestParam(TARGET_PATH_KEY) String targetPath,
                                HttpServletRequest request,
                                Model model) {

        try {
            userAccessControlService.doLoginCheck(username, password);
            AppUser user = (AppUser) userAccessControlService.getCurrentUser();
            user.setLoginIp(RequestIpAddressUtil.getClientIp(request));
            logService.info(user, "user", "login_success", "登录成功");
            if (EasyStringUtil.isBlank(targetPath)) {
                String savedUrlBeforeLogin = userAccessControlService.getSavedUrlBeforeLogin(request);
                targetPath = EasyStringUtil.isNotBlank(savedUrlBeforeLogin) ? savedUrlBeforeLogin : "/";
            }
            String contextPath = request.getContextPath();
            if (targetPath.contains(contextPath)) {
                targetPath = targetPath.replace(contextPath, "");
            }
            return "redirect:" + targetPath;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("last_username", username);
            return "/login/index";
        }

    }

}
