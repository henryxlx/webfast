package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.kernel.service.AppBlockService;
import com.jetwinner.webfast.kernel.service.AppNavigationService;
import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastSiteDefaultController")
public class DefaultController implements BlockRenderController {

    private final AppBlockService blockService;
    private final AppNavigationService navigationService;

    public DefaultController(AppBlockService blockService, AppNavigationService navigationService) {
        this.blockService = blockService;
        this.navigationService = navigationService;
    }

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("home_top_banner", blockService.getBlockByCode("home_top_banner"));
        return "/default/index";
    }

    @RequestMapping("/default/topNavigation")
    @BlockRenderMethod
    public String topNavigationAction(String siteNav, Model model) {
        model.addAttribute("siteNav", siteNav);
        model.addAttribute("navigations", navigationService.getNavigationsTreeByType("top"));
        return "/default/top-navigation.ftl";
    }
}
