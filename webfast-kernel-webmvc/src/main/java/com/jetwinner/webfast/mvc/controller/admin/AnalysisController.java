package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminAnalysisController")
public class AnalysisController {

    @RequestMapping("/admin/operation/analysis/register/{tab}")
    public String registerPage(@PathVariable String tab) {
        return "/admin/operation/analysis/register";
    }

    @RequestMapping("/admin/operation/analysis/login/{tab}")
    public String loginPage(@PathVariable String tab) {
        return "/admin/operation/analysis/login";
    }

    @RequestMapping("/admin/operation/analysis/user-sum/{tab}")
    public String userSumPage(@PathVariable String tab) {
        return "/admin/operation/analysis/user-sum";
    }

}
