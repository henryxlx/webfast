package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.service.AppCategoryService;
import com.jetwinner.webfast.kernel.service.AppContentService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Map<String, Object> conditions = ParamMap.toMap(request.getParameterMap());

        Paginator paginator = new Paginator(
                request,
                contentService.searchContentCount(conditions),
                20);

        List<Map<String, Object>> contents = contentService.searchContents(
                conditions,
                new ParamMap().add("createdTime", "DESC"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        Set<Object> userIds = contents.stream().map(x -> x.get("userId")).collect(Collectors.toSet());
        model.addAttribute("users", userService.findUsersByIds(userIds));

        Set<Object> categoryIds = contents.stream().map(x -> x.get("categoryId")).collect(Collectors.toSet());
        model.addAttribute("categories", categoryService.findCategoriesByIds(categoryIds));

        model.addAttribute("paginator", paginator);
        model.addAttribute("contents", contents);
        return "/admin/content/index";
    }
}
