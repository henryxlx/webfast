package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.module.bigapp.service.AppCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminCategoryController")
public class CategoryController {

    private final AppCategoryService categoryService;

    public CategoryController(AppCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/admin/category")
    public String indexPage(@RequestParam(required = false) String group,
                            @RequestParam(required = false) String layout,
                            Model model) {

        Map<String, Object> groupModel = categoryService.getGroupByCode(group);
        if (groupModel == null || groupModel.isEmpty()) {
            // throw new RuntimeGoingException("Category group not found.");
            groupModel.put("id", 1);
        }
        model.addAttribute("group", groupModel);
        model.addAttribute("categories", categoryService.getCategoryTree(groupModel.get("id")));
        model.addAttribute("layout", layout);
        return "/admin/category/index";
    }
}
