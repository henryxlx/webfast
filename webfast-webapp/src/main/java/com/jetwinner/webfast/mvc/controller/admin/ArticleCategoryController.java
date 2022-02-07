package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.service.AppArticleCategoryService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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
    public String indexAction(Model model) {
        model.addAttribute("categories", categoryService.getCategoryTree());
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

    @PostMapping("/admin/article/category/create")
    public String createAction(@RequestParam Map<String, Object> formData, Model model) {
        categoryService.createCategory(formData);
        List<Map<String, Object>> categories = categoryService.getCategoryTree();
        model.addAttribute("categories", categories);
        model.addAttribute("categoryTree", categories);
        return "/admin/article/category/tbody";
    }

    @GetMapping("/admin/article/category/checkcode")
    @ResponseBody
    public Map<String, Object> checkCodeAction(String value, String exclude) {
        String code = value;
        boolean avaliable = categoryService.isCategoryCodeAvaliable(code, exclude);
        return avaliable ? new ParamMap().add("success", Boolean.TRUE).add("message", "").toMap() :
                new ParamMap().add("success", Boolean.FALSE).add("message", "编码已被占用，请换一个。").toMap();
    }

    @GetMapping("/admin/article/category/checkparentid")
    @ResponseBody
    public Map<String, Object> checkParentIdAction(Integer value, Integer currentId) {
        int selectedParentId = value;
        ParamMap response;
        if(currentId == selectedParentId && selectedParentId != 0){
            response = new ParamMap().add("success", Boolean.FALSE).add("message", "不能选择自己作为父栏目");
        } else {
            response = new ParamMap().add("success", Boolean.TRUE).add("message", "");
        }
        return response.toMap();
    }
}
