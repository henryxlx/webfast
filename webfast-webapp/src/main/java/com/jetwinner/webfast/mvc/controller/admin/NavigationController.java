package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.service.AppNavigationService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminNavigationController")
public class NavigationController {

    private final AppNavigationService navigationService;

    public NavigationController(AppNavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @RequestMapping("/admin/navigation")
    public String indexPage(@RequestParam(defaultValue = "top") String type, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("navigations", navigationService.getNavigationsListByType(type));
        return "/admin/navigation/index";
    }

    @GetMapping("/admin/navigation/create")
    public String createPage(HttpServletRequest request, Model model) {
        Map<String, Object> navigation = MapUtil.newHashMap();
        navigation.put("id", 0);
        navigation.put("name", "");
        navigation.put("url", "");
        navigation.put("isNewWin", 0);
        navigation.put("isOpen", 1);
        navigation.put("type", request.getParameter("type"));
        navigation.put("parentId", ServletRequestUtils.getIntParameter(request, "parentId", 0));
        model.addAttribute("navigation", navigation);
        return "/admin/navigation/navigation-modal";
    }

    @PostMapping("/admin/navigation/create")
    @ResponseBody
    public String createAction(HttpServletRequest request) {
        Map<String, Object> navigation = ParamMap.toPostDataMap(request);
        navigationService.createNavigation(navigation);
        return "success";
    }
}
