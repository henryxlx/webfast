package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.service.AppCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
        if (MapUtil.isEmpty(groupModel)) {
            groupModel = new HashMap<>(2);
            groupModel.put("code", group);
            groupModel.put("name", "新的分类(category_group数据表未保存) " + group);
            groupModel.put("depth", 2);
            groupModel.put("id", 1);
        }
        model.addAttribute("group", groupModel);
        model.addAttribute("categories", categoryService.getCategoryTree(groupModel.get("id")));
        model.addAttribute("layout", layout);
        return "/admin/category/index";
    }

    @RequestMapping("/admin/category/create")
    public String createAction(HttpServletRequest request, Model model) {
        if ("POST" .equals(request.getMethod())) {
            Map<String, Object> category = ParamMap.toFormDataMap(request);
            categoryService.createCategory(AppUser.getCurrentUser(request), category);
            return renderTbody(category.get("groupId"), model);
        }

        Map<String, Object> category = new ParamMap()
                .add("name", "")
                .add("code", "")
                .add("description", "")
                .add("groupId", ServletRequestUtils.getIntParameter(request, "groupId", 0))
                .add("parentId", ServletRequestUtils.getIntParameter(request, "parentId", 0))
                .add("weight", 0)
                .add("icon", "").toMap();

        model.addAttribute("category", category);
        return "/admin/category/modal";
    }

    private String renderTbody(Object groupId, Model model) {
        model.addAttribute("group", categoryService.getGroup(groupId));
        model.addAttribute("categories", categoryService.getCategoryTree(groupId));
        return "/admin/category/tbody";
    }

    @RequestMapping("/admin/category/checkcode")
    @ResponseBody
    public Map<String, Object> checkCodeAction(HttpServletRequest request) {
        ParamMap response = new ParamMap();
        String code = request.getParameter("value");
        String exclude = request.getParameter("exclude");

        boolean avaliable = categoryService.isCategoryCodeAvaliable(code, exclude);
        if (avaliable) {
            response.add("success", Boolean.TRUE).add("message", "");
        } else {
            response.add("success", Boolean.FALSE).add("message", "编码已被占用，请换一个。");
        }
        return response.toMap();
    }

    @RequestMapping("/admin/category/{id}/edit")
    public String editAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Map<String, Object> category = categoryService.getCategory(id);
        if (category == null || category.isEmpty()) {
            throw new RuntimeGoingException("Category not found!");
        }

        if ("POST".equals(request.getMethod())) {
            category = ParamMap.toFormDataMap(request);
            categoryService.updateCategory(AppUser.getCurrentUser(request), id, category);
            return renderTbody(category.get("groupId"), model);
        }

        model.addAttribute("category", category);
        return "/admin/category/modal";
    }

    @RequestMapping("/admin/category/{id}/delete")
    public String deleteAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Map<String, Object> category = categoryService.getCategory(id);
        categoryService.deleteCategory(AppUser.getCurrentUser(request), id);
        return renderTbody(category.get("groupId"), model);
    }

}
