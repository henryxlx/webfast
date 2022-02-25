package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.service.AppArticleCategoryService;
import com.jetwinner.webfast.module.bigapp.service.AppArticleService;
import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Controller("webfastSiteArticleController")
public class ArticleController implements BlockRenderController {

    private final AppArticleService articleService;
    private final AppArticleCategoryService categoryService;
    private final AppSettingService settingService;
    private final AppTagService tagService;

    public ArticleController(AppArticleService articleService,
                             AppArticleCategoryService categoryService,
                             AppSettingService settingService, AppTagService tagService) {

        this.articleService = articleService;
        this.categoryService = categoryService;
        this.settingService = settingService;
        this.tagService = tagService;
    }

    @RequestMapping("/article")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> settingMap = settingService.get("article");
        if (settingMap == null || settingMap.isEmpty()) {
            settingMap = new ParamMap().add("name", "资讯频道").add("pageNums", 20).toMap();
        }
        model.addAttribute("articleSetting", settingMap);

        model.addAttribute("categoryTree", categoryService.getCategoryTree());

        Map<String, Object> conditions = new ParamMap().add("status", "published").toMap();
        Paginator paginator = new Paginator(request,
                articleService.searchArticlesCount(conditions),
                ValueParser.parseInt(settingMap.get("pageNums"))
        );
        model.addAttribute("paginator", paginator);

        List<Map<String, Object>> latestArticles = articleService.searchArticles(
                conditions, "published",
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        model.addAttribute("latestArticles", latestArticles);

        model.addAttribute("categories",
                categoryService.findCategoriesByIds(ArrayToolkit.column(latestArticles, "categoryId")));

        Map<String, Object> featuredConditions = new ParamMap()
                .add("status", "published")
                .add("featured", 1)
                .add("hasPicture", 1).toMap();
        model.addAttribute("featuredArticles",
                articleService.searchArticles(featuredConditions, "normal", 0, 5));

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
        model.addAttribute("articleSetting", setting);
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

    @RequestMapping("/article/{id}")
    public ModelAndView detailAction(@PathVariable Integer id) {
        Map<String, Object> article = articleService.getArticle(id);
        if (article.isEmpty()) {
            throw new RuntimeGoingException("文章已删除或者未发布！");
        }

        if (!"published".equals(article.get("status"))) {
            return BaseControllerHelper.createMessageResponse("error","文章不是发布状态，请查看！");
        }

        ModelAndView mav = new ModelAndView("/article/detail");
        Map<String, Object> settingMap = settingService.get("article");
        if (settingMap == null || settingMap.isEmpty()) {
            settingMap = new ParamMap().add("name", "资讯频道").add("pageNums", 20).toMap();
        }
        mav.addObject("articleSetting", settingMap);

        Map<String, Object> conditions = new ParamMap().add("status", "published").toMap();

        Object currentArticleId = article.get("id");
        mav.addObject("articlePrevious", articleService.getArticlePrevious(currentArticleId));
        mav.addObject("articleNext", articleService.getArticleNext(currentArticleId));
        mav.addObject("article", article);

        mav.addObject("categoryTree", categoryService.getCategoryTree());

        String[] tagIds = new String[0];
        if(article.get("tagIds") != null){
            tagIds = String.valueOf(article.get("tagIds").toString()).split(",");
        }
        List<Map<String, Object>> tags = tagService.findTagsByIds(tagIds);
        mav.addObject("tags", tags);

        String seoKeyword = "";
        if(tags != null){
            Set<Object> seoKeywordSet = ArrayToolkit.column(tags, "name");
            seoKeyword = seoKeywordSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
        }
        mav.addObject("seoKeyword", seoKeyword);
        mav.addObject("seoDesc", article.get("body"));

        articleService.hitArticle(id);

        Map<String, Object> category = categoryService.getCategory(article.get("categoryId"));
        mav.addObject("categoryName", category.get("name"));
        mav.addObject("categoryCode", category.get("code"));
        mav.addObject("breadcrumbs", categoryService.findCategoryBreadcrumbs(category.get("id")));
        return mav;
    }

    @RequestMapping("/article/popularArticlesBlock")
    @BlockRenderMethod
    public String popularArticlesBlockAction(Model model) {
        Map<String, Object> conditions = new ParamMap()
                .add("type", "article")
                .add("status", "published").toMap();
        model.addAttribute("articles",
                articleService.searchArticles(conditions, "popular", 0, 10));

        return "/article/popular-articles-block";
    }

    @RequestMapping("/article/recommendArticlesBlock")
    @BlockRenderMethod
    public String recommendArticlesBlockAction(Model model) {
        Map<String, Object> conditions = new ParamMap()
                .add("type", "article")
                .add("status", "published")
                .add("promoted", 1).toMap();
        model.addAttribute("articles",
                articleService.searchArticles(conditions, "normal", 0, 10));

        return "/article/recommend-articles-block";
    }
}
