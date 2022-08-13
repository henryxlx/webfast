package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.module.bigapp.service.AppCategoryService;
import com.jetwinner.webfast.module.bigapp.service.AppContentService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.service.content.type.ContentTypeFactory;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Controller("webfastAdminContentController")
public class ContentController {

    private final AppContentService contentService;
    private final AppUserService userService;
    private final AppCategoryService categoryService;

    public ContentController(AppContentService contentService,
                             AppUserService userService,
                             AppCategoryService categoryService) {

        this.contentService = contentService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/admin/content")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> conditions = ParamMap.toConditionMap(request);

        Paginator paginator = new Paginator(
                request,
                contentService.searchContentCount(conditions),
                20);

        List<Map<String, Object>> contents = contentService.searchContents(
                conditions,
                new OrderBy().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        Set<Object> userIds = contents.stream().map(x -> x.get("userId")).collect(Collectors.toSet());
        model.addAttribute("users", userService.findUsersByIds(userIds));

        Set<Object> categoryIds = contents.stream().map(x -> x.get("categoryId")).collect(Collectors.toSet());
        model.addAttribute("categories", categoryService.findCategoriesByIds(categoryIds));

        model.addAttribute(Paginator.MODEL_ATTR_NAME, paginator);
        model.addAttribute("contents", contents);
        return "/admin/content/index";
    }

    @GetMapping("/admin/content/create")
    public String createPage(String type, Model model) {
        model.addAttribute("type", ContentTypeFactory.create(type));
        return "/admin/content/content-modal";
    }

    @GetMapping("/admin/content/{id}/edit")
    public String editPage(@PathVariable Integer id, Model model) {
        Map<String, Object> content = contentService.getContent(id);
        String type = String.valueOf(content.get("type"));
        model.addAttribute("type", ContentTypeFactory.create(type));
        model.addAttribute("content", content);
        return "/admin/content/content-modal";
    }

    @PostMapping("/admin/content/{id}/trash")
    @ResponseBody
    public Boolean trashAction(@PathVariable Integer id, HttpServletRequest request) {
        contentService.trashContent(AppUser.getCurrentUser(request), id);
        return Boolean.TRUE;
    }

    @RequestMapping("/admin/content/{id}/publish")
    @ResponseBody
    public Boolean publishAction(@PathVariable Integer id, HttpServletRequest request) {
        contentService.publishContent(AppUser.getCurrentUser(request), id);
        return Boolean.TRUE;
    }
}
