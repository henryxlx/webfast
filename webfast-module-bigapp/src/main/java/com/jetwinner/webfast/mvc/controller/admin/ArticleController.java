package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.service.AppArticleCategoryService;
import com.jetwinner.webfast.module.bigapp.service.AppArticleService;
import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final AppTagService tagService;
    private final AppSettingService settingService;

    public ArticleController(AppArticleService articleService,
                             AppArticleCategoryService categoryService,
                             AppTagService tagService,
                             AppSettingService settingService) {

        this.articleService = articleService;
        this.categoryService = categoryService;
        this.tagService = tagService;
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
        articleService.createArticle(AppUser.getCurrentUser(request), article);
        return "redirect:/admin/article";
    }

    @RequestMapping("/admin/article/{id}/edit")
    public String editAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Map<String, Object> article = articleService.getArticle(id);
        if (article.isEmpty()) {
            throw new RuntimeGoingException("文章已删除或者未发布！");
        }

        if ("POST".equals(request.getMethod())) {
            Map<String, Object> formData = ParamMap.toFormDataMap(request);
            articleService.updateArticle(AppUser.getCurrentUser(request), id, formData);
            return "redirect:/admin/article";
        }

        String[] tagIds = new String[0];
        if (article.get("tagIds") != null) {
            tagIds = String.valueOf(article.get("tagIds")).split(",");
        }
        List<Map<String, Object>> tags = tagService.findTagsByIds(tagIds);
        model.addAttribute("tagNames", ArrayToolkit.column(tags, "name"));

        model.addAttribute("category", categoryService.getCategory(article.get("categoryId")));

        model.addAttribute("categoryTree", categoryService.getCategoryTree());
        model.addAttribute("article", article);

        return "/admin/article/article-modal";
    }

    @RequestMapping("/admin/article/{id}/property/set/{property}")
    @ResponseBody
    public Boolean setArticlePropertyAction(@PathVariable Integer id, @PathVariable String property, HttpServletRequest request) {
        articleService.setArticleProperty(AppUser.getCurrentUser(request), id, property);
        return Boolean.TRUE;
    }

    @RequestMapping("/admin/article/{id}/property/cancel/{property}")
    @ResponseBody
    public Boolean cancelArticlePropertyAction(@PathVariable Integer id, @PathVariable String property, HttpServletRequest request) {
        articleService.cancelArticleProperty(AppUser.getCurrentUser(request), id, property);
        return Boolean.TRUE;
    }

    @RequestMapping("/admin/article/{id}/trash")
    @ResponseBody
    public Boolean trashAction(@PathVariable Integer id, HttpServletRequest request) {
        articleService.trashArticle(AppUser.getCurrentUser(request), id);
        return Boolean.TRUE;
    }

    @RequestMapping("/admin/article/{id}/delete")
    @ResponseBody
    public Map<String, Object> deleteAction(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids");
        String id = request.getParameter("id");

        if (id != null) {
            ids = new String[]{id};
        }
        int result = articleService.deleteArticlesByIds(AppUser.getCurrentUser(request), ids);
        if (result > 0) {
            return new ParamMap().add("status", "success").toMap();
        } else {
            return new ParamMap().add("status", "failed").toMap();
        }
    }

    @RequestMapping("/admin/article/{id}/publish")
    @ResponseBody
    public Boolean publishAction(@PathVariable Integer id, HttpServletRequest request) {
        articleService.publishArticle(AppUser.getCurrentUser(request), id);
        return Boolean.TRUE;
    }

    @RequestMapping("/admin/article/{id}/unpublish")
    @ResponseBody
    public Boolean unpublishAction(@PathVariable Integer id, HttpServletRequest request) {
        articleService.unpublishArticle(AppUser.getCurrentUser(request), id);
        return Boolean.TRUE;
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
            BaseControllerHelper.setFlashMessage("success", "资讯频道设置已保存！", request.getSession());
        }
        model.addAttribute("articleSetting", articleSetting);
        return "/admin/article/setting";
    }
}
