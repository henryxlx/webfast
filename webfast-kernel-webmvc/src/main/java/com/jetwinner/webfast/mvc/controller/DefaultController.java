package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.kernel.service.AppBlockService;
import com.jetwinner.webfast.kernel.service.AppNavigationService;
import com.jetwinner.webfast.mvc.PageActionModelService;
import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.context.ApplicationContext;
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
    private final ApplicationContext applicationContext;

    public DefaultController(AppBlockService blockService,
                             AppNavigationService navigationService,
                             ApplicationContext applicationContext) {

        this.blockService = blockService;
        this.navigationService = navigationService;
        this.applicationContext = applicationContext;
    }

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("home_top_banner", blockService.getBlockByCode("home_top_banner"));
        String[] beanNames = applicationContext.getBeanNamesForType(PageActionModelService.class);
        if (beanNames != null) {
            for (String beanName : beanNames) {
                PageActionModelService indexPageAction =
                        (PageActionModelService) applicationContext.getBean(beanName);
                indexPageAction.addMoreObject(model);
            }
        }

        return "/default/index";
    }

    @RequestMapping("/default/topNavigation")
    @BlockRenderMethod
    public String topNavigationAction(String siteNav, Model model) {
        model.addAttribute("siteNav", siteNav);
        model.addAttribute("navigations", navigationService.getNavigationsTreeByType("top"));
        return "/default/top-navigation";
    }

    @RequestMapping("/default/footNavigation")
    @BlockRenderMethod
    public String footNavigationAction(Model model) {
        model.addAttribute("navigations",
                navigationService.findNavigationsByType("foot", 0, 100));
        return "/default/foot-navigation";
    }
}
