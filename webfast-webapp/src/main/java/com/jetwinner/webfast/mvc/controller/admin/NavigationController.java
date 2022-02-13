package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.model.AppModelNavigation;
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
        Integer parentId = ServletRequestUtils.getIntParameter(request, "parentId", 0);
        navigation.put("parentId", parentId);
        model.addAttribute("navigation", navigation);
        model.addAttribute("parentNavigation", navigationService.getNavigationById(parentId));
        return "/admin/navigation/navigation-modal";
    }

    @PostMapping("/admin/navigation/create")
    @ResponseBody
    public String createAction(HttpServletRequest request) {
        Map<String, Object> navigation = ParamMap.toFormDataMap(request);
        navigationService.createNavigation(AppUser.getCurrentUser(request), navigation);
        return "success";
    }

    @RequestMapping("/admin/navigation/{id}/delete")
    @ResponseBody
    public Map<String, Object> deleteAction(@PathVariable Integer id) {
        int result = navigationService.deleteNavigation(id);
        if(result > 0){
            return new ParamMap().add("status", "ok").toMap();
        } else {
            return new ParamMap().add("status", "error").toMap();
        }
    }

    @GetMapping("/admin/navigation/{id}/update")
    public String updatePage(@PathVariable Integer id, Model model) {
        AppModelNavigation navigation = navigationService.getNavigationById(id);
        model.addAttribute("navigation", navigation);
        model.addAttribute("parentNavigation",
                navigationService.getNavigationById(navigation.getParentId()));
        return "/admin/navigation/navigation-modal";
    }

    @PostMapping("/admin/navigation/{id}/update")
    @ResponseBody
    public String updateAction(@PathVariable Integer id, HttpServletRequest request) {
        int result = navigationService.updateNavigation(AppUser.getCurrentUser(request),
                id, ParamMap.toFormDataMap(request));
        return result > 0 ? "success" : "failure";
    }
}
