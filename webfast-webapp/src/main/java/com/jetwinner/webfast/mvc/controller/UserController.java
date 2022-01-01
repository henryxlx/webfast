package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteUserController")
public class UserController implements BlockRenderController {

    @RequestMapping("/user/{id}")
    public String userHomePage(@PathVariable Integer id) {
        return "/user/user-home";
    }

    @GetMapping("/user/headerBlock")
    @BlockRenderMethod
    public String showHeaderBlock() {
        return "/user/header-block";
    }
}
