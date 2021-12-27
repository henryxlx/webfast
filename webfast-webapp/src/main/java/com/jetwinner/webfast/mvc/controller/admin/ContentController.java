package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xulixin
 */
@Controller("webfastAdminContentController")
public class ContentController {

    private final AppContentService contentService;

    public ContentController(AppContentService contentService) {
        this.contentService = contentService;
    }

    @RequestMapping("/admin/content")
    public String indexPage(Model model) {
        model.addAttribute("contents", contentService.findAllContent());
        return "/admin/content/index";
    }
}
