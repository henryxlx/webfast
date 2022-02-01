package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteUserController")
public class UserController implements BlockRenderController {

    private final AppUserService userService;

    public UserController(AppUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/{id}")
    public String userHomePage(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "/user/user-home";
    }

    @RequestMapping("/user/{id}/{pageNav}")
    public String userHomeTagPage(@PathVariable Integer id, @PathVariable String pageNav, Model model) {
        model.addAttribute("pageNav", pageNav);
        model.addAttribute("user", userService.getUser(id));
        return "/user/user-home";
    }

    @GetMapping("/user/headerBlock")
    @BlockRenderMethod
    public String showHeaderBlock() {
        return "/user/header-block";
    }
}
