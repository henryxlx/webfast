package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.module.service.AppArticleCategoryService;
import com.jetwinner.webfast.module.service.AppArticleService;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xulixin
 */
@Controller("webfastSiteArticleController")
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

    @RequestMapping("/article")
    public String indexPage() {
        return "/article/index";
    }

    @RequestMapping("/article/category/{categoryCode}")
    public String categoryPage(@PathVariable String categoryCode, HttpServletRequest request, Model model) {
        Map<String, Object> category = categoryService.getCategoryByCode(categoryCode);

        if (category == null) {
            throw new RuntimeGoingException("资讯栏目页面不存在");
        }

        Map<String, Object> conditions = new ParamMap()
                .add("categoryId", category.get("id"))
                .add("includeChildren", Boolean.TRUE)
                .add("status", "published").toMap();


        List<Map<String, Object>> categoryTree = categoryService.getCategoryTree();

        Map<String, Object> rootCategory = getRootCategory(categoryTree, category);
        model.addAttribute("subCategories", getSubCategories(categoryTree, rootCategory));
        model.addAttribute("rootCategory", rootCategory);

        Map<String, Object> setting = settingService.get("article");
        if (setting == null || setting.size() == 0) {
            setting = new ParamMap().add("name", "资讯频道").add("pageNums", 20).toMap();
        }

        Paginator paginator = new Paginator(request,
                articleService.searchArticlesCount(conditions),
                ValueParser.parseInt(setting.get("pageNums")));

        List<Map<String, Object>> articles = articleService.searchArticles(
                conditions, "published",
                paginator.getOffsetCount(),
                paginator.getPerPageCount());

        Set<Object> categoryIds = ArrayToolkit.column(articles, "categoryId");
        model.addAttribute("categories", categoryService.findCategoriesByIds(categoryIds));
        model.addAttribute("categoryTree", categoryTree);
        model.addAttribute("categoryCode", categoryCode);
        model.addAttribute("category", category);
        model.addAttribute("articles", articles);
        model.addAttribute("paginator", paginator);
        model.addAttribute("setting", setting);
        return "/article/list";
    }

    private Map<String, Object> getRootCategory(List<Map<String, Object>> categoryTree, Map<String, Object> category) {
        boolean start = false;
        int size = categoryTree != null ? categoryTree.size() : 0;
        if (size > 0) {
            for(int i = size - 1; i >= 0; i--) {
                Map<String, Object> treeCategory = categoryTree.get(i);
                if (Objects.equals(treeCategory.get("id"), category.get("id"))) {
                    start = true;
                }

                if (start && ValueParser.parseInt(treeCategory.get("depth")) ==1) {
                    return treeCategory;
                }
            }
        }
        return null;
    }

    private List<Map<String, Object>> getSubCategories(List<Map<String, Object>> categoryTree, Map<String, Object> rootCategory) {
        List<Map<String, Object>> categories = new ArrayList<>();
        boolean start = false;
        for (Map<String, Object> treeCategory : categoryTree) {
            if (start && (ValueParser.parseInt(treeCategory.get("depth")) == 1) &&
                    (!Objects.equals(treeCategory.get("id"), rootCategory.get("id")))) {
                break;
            }

            if (treeCategory.get("id") == rootCategory.get("id")) {
                start = true;
            }

            if (start == true) {
                categories.add(treeCategory);
            }
        }
        return categories;
    }
}
