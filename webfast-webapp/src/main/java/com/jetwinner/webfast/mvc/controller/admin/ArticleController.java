package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.session.FlashMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("adminArticleController")
public class ArticleController {

    private final AppSettingService settingService;

    public ArticleController(AppSettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/admin/article")
    public String indexPage() {
        return "/admin/article/index";
    }

    @RequestMapping("/admin/article/setting")
    public String settingPage(HttpServletRequest request, Model model) {
        Map<String, Object> articleSetting = settingService.get("article");

        Map<String, Object> defaultMap = new ParamMap()
                .add("name", "资讯频道").add("pageNums", 20).toMap();

        defaultMap.putAll(articleSetting);
        if ("POST".equals(request.getMethod())) {
            articleSetting = ParamMap.toFilterPostDataMap(request, "name", "pageNums");
            settingService.set("article", articleSetting);
            // logService.info("article", "update_settings", "更新资讯频道设置", articleSetting);
            FlashMessageUtil.setFlashMessage("success", "资讯频道设置已保存！", request.getSession());
        };
        model.addAttribute("articleSetting", defaultMap);
        return "/admin/article/setting";
    }
}
