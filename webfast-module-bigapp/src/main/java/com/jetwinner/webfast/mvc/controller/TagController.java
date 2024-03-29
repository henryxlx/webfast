package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastSiteTagController")
public class TagController {

    private final AppTagService tagService;

    public TagController(AppTagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping("/tag")
    public String indexPage(Model model) {
        model.addAttribute("tags", this.tagService.findAllTags(0, 100));
        return "/tag/index";
    }

    @RequestMapping("/tag/match_jsonp")
    @ResponseBody
    public List<Map<String, Object>> matchAction(@RequestParam(name = "q") String queryString,
                                                 @RequestParam(required = false) String callback) {

        return tagService.getTagByLikeName(queryString);
    }
}
