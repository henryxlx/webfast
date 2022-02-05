package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppArticleCategoryService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author xulixin
 */
@Controller("adminArticleCategoryController")
public class ArticleCategoryController {

    private final AppArticleCategoryService categoryService;

    public ArticleCategoryController(AppArticleCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/article/category")
    public String indexAction() {
        return "/admin/article/category/index";
    }

    @GetMapping("/admin/article/category/create")
    public String createPage(@RequestParam(defaultValue = "0") Integer parentId, Model model) {
        Map<String, Object> category = new ParamMap()
                .add("id", 0)
                .add("name", "")
                .add("code", "")
                .add("parentId", parentId)
                .add("weight", 0)
                .add("publishArticle", 1)
                .add("seoTitle", "")
                .add("seoKeyword", "")
                .add("seoDesc", "")
                .add("published", 1).toMap();
        model.addAttribute("category", category);

        model.addAttribute("categoryTree", categoryService.getCategoryTree());
        return "/admin/article/category/modal";
    }
}
