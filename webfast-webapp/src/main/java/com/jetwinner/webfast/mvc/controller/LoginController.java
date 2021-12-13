package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.exception.ActionGraspException;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xulixin
 */
@Controller("webfastSiteLoginController")
public class LoginController {

    private UserAccessControlService userAccessControlService;

    public LoginController(UserAccessControlService userAccessControlService) {
        this.userAccessControlService = userAccessControlService;
    }

    @GetMapping("/login")
    public String indexPage(String targetPath) {
        return "/login/index";
    }

    @PostMapping("/login")
    public String doLoginAction(@RequestParam("_username") String username,
                                @RequestParam("_password") String password,
                                @RequestParam("_target_path") String targetPath,
                                Model model) {

        try {
            userAccessControlService.doLoginCheck(username, password);
            if (EasyStringUtil.isBlank(targetPath)) {
                targetPath = "/";
            }
            return "redirect:" + targetPath;
        } catch (ActionGraspException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("last_username", username);
            return "/login/index";
        }

    }

}
