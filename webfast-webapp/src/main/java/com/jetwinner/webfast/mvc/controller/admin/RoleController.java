package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.model.AppModelRole;
import com.jetwinner.webfast.kernel.service.AppRoleService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Controller("webfastAdminRoleController")
public class RoleController {

    private final AppRoleService roleService;
    private final AppUserService userService;

    public RoleController(AppRoleService roleService, AppUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/admin/role")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> fields = ArrayToolkit.filterRequestMap(request.getParameterMap(), "keyword", "keywordType");
        Map<String, Object> conditions = new ParamMap().toMap();

        if (EasyStringUtil.isNotBlank(fields.get("keywordType"))) {
            conditions.put(String.valueOf(fields.get("keywordType")), fields.get("keyword"));
        }
        Paginator paginator = new Paginator(request, roleService.searchRolesCount(conditions), 30);

        List<AppModelRole> roles = roleService.searchRoles(conditions, OrderBy.builder().add("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        Set<Object> userIds = ArrayToolkitOnJava8.column(roles, AppModelRole::getCreatedUserId);
        model.addAttribute("users", userService.findUsersByIds(userIds));
        model.addAttribute("roles", roles);
        model.addAttribute("paginator", paginator);
        return "/admin/role/index";
    }

    @GetMapping("/admin/role/create")
    public String createRolePage() {
        return "/admin/role/create-model";
    }
}
