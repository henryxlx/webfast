package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.service.AppArticleCategoryService;
import com.jetwinner.webfast.kernel.service.AppArticleService;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.session.FlashMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Controller("adminArticleController")
public class ArticleController {

    private final AppArticleService articleService;
    private final AppArticleCategoryService categoryService;
    private final AppSettingService settingService;

    public ArticleController(AppArticleService articleService,
                             AppArticleCategoryService categoryService,
                             AppSettingService settingService) {

        this.articleService = articleService;
        this.categoryService = categoryService;
        this.settingService = settingService;
    }

    @GetMapping("/admin/article")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> conditions = ParamMap.toConditionMap(request);
        int categoryId = 0;
        if (EasyStringUtil.isNotBlank(conditions.get("categoryId"))) {
            conditions.put("includeChildren", Boolean.TRUE);
            categoryId = ValueParser.parseInt(conditions.get("categoryId"));
        }

        Paginator paginator = new Paginator(request,
                articleService.searchArticlesCount(conditions),
                20);

        List<Map<String, Object>> articles = articleService.searchArticles(
                conditions,
                "normal",
                paginator.getOffsetCount(),
                paginator.getPerPageCount());

        Set<Object> categoryIds = ArrayToolkit.column(articles, "categoryId");
        model.addAttribute("categories", categoryService.findCategoriesByIds(categoryIds));
        model.addAttribute("categoryTree", categoryService.getCategoryTree());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("articles", articles);
        model.addAttribute("paginator", paginator);
        return "/admin/article/index";
    }

    @GetMapping("/admin/article/create")
    public String createPage(Model model) {
        model.addAttribute("categoryTree", categoryService.getCategoryTree());
        model.addAttribute("category",
                new ParamMap().add("id", 0).add("parentId", 0).toMap());
        return "/admin/article/article-modal";
    }

    @PostMapping("/admin/article/create")
    public String createAction(HttpServletRequest request) {
        Map<String, Object> article = ParamMap.toFormDataMap(request);
        article.put("tags", String.join(",", request.getParameterValues("tags")));
        articleService.createArticle(article, AppUser.getCurrentUser(request));
        return "redirect:/admin/article";
    }

    @RequestMapping("/admin/article/setting")
    public String settingPage(HttpServletRequest request, Model model) {
        Map<String, Object> settingMapSaved = settingService.get("article");

        Map<String, Object> articleSetting = new ParamMap()
                .add("name", "资讯频道").add("pageNums", 20).toMap();
        articleSetting.putAll(settingMapSaved);

        if ("POST".equals(request.getMethod())) {
            articleSetting = ParamMap.toCustomFormDataMap(request, "name", "pageNums");
            settingService.set("article", articleSetting);
            // logService.info("article", "update_settings", "更新资讯频道设置", articleSetting);
            FlashMessageUtil.setFlashMessage("success", "资讯频道设置已保存！", request.getSession());
        }
        model.addAttribute("articleSetting", articleSetting);
        return "/admin/article/setting";
    }
}
