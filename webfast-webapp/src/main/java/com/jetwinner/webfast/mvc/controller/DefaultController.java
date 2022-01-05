package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.kernel.service.AppBlockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteDefaultController")
public class DefaultController {

    private final AppBlockService blockService;

    public DefaultController(AppBlockService blockService) {
        this.blockService = blockService;
    }

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("home_top_banner", blockService.getBlockByCode("home_top_banner"));
        return "/default/index";
    }
}
