package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.module.service.AppContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastSiteContentController")
public class ContentController {

    private final AppContentService contentService;

    public ContentController(AppContentService contentService) {
        this.contentService = contentService;
    }

    @RequestMapping("/page/{alias}")
    public String pageShowAction(@PathVariable String alias, Model model) {
        Map<String, Object> content = getContentByAlias("page", alias);

        String template;
        if ("default".equals(content.get("template"))) {
            template = "/content/page-show";
        } else {
            alias = String.valueOf(EasyStringUtil.isNotBlank(content.get("alias")) ?
                    content.get("alias") : content.get("id"));
            template = String.format("@customize/content/page/%s/index", alias);
        }

        model.addAttribute("content", content);
        return template;
    }

    private Map<String, Object> getContentByAlias(String type, String alias) {
        Map<String, Object> content;
        if (EasyStringUtil.isNumeric(alias)) {
            content = contentService.getContent(Integer.valueOf(alias));
        } else {
            content = contentService.getContentByAlias(alias);
        }

        if (MapUtil.isEmpty(content) || !type.equals(content.get("type"))) {
            throw new RuntimeGoingException("getContentByAlias failed.");
        }

        return content;
    }
}
