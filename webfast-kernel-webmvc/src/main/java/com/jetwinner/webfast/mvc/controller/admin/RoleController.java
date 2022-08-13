package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppRole;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.AppPermissionDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.service.AppRoleService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    private final AppPermissionDao permissionDao;

    public RoleController(AppRoleService roleService, AppUserService userService,
                          AppPermissionDao permissionDao) {

        this.roleService = roleService;
        this.userService = userService;
        this.permissionDao = permissionDao;
    }

    @GetMapping("/admin/role")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> fields = ArrayToolkit.filterRequestMap(request.getParameterMap(), "keyword", "keywordType");
        Map<String, Object> conditions = new ParamMap().toMap();

        if (EasyStringUtil.isNotBlank(fields.get("keywordType"))) {
            conditions.put(String.valueOf(fields.get("keywordType")), fields.get("keyword"));
        }
        Paginator paginator = new Paginator(request, roleService.searchRolesCount(conditions), 30);

        List<AppRole> roles = roleService.searchRoles(conditions, new OrderBy().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        Set<Object> userIds = ArrayToolkitOnJava8.column(roles, AppRole::getCreatedUserId);
        model.addAttribute("users", userService.findUsersByIds(userIds));
        model.addAttribute("roles", roles);
        model.addAttribute(Paginator.MODEL_ATTR_NAME, paginator);
        return "/admin/role/index";
    }

    @GetMapping("/admin/role/create")
    public String createRolePage() {
        return "/admin/role/role-modal";
    }

    @RequestMapping("/admin/role/checkname")
    @ResponseBody
    public Map<String, Object> checkRoleNameAction(@RequestParam("value") String roleName, String exclude) {
        Map<String, Object> map = new HashMap<>(2);
        boolean available = roleService.isRoleNameAvailable(roleName, exclude);

        if (available) {
            map.put("success", Boolean.TRUE);
            map.put("message", "");
        } else {
            map.put("success", Boolean.FALSE);
            map.put("message", "角色已存在");
        }
        return map;
    }

    @PostMapping("/admin/role/create")
    public String createRoleAction(String roleName, String label, HttpServletRequest request, Model model) {
        if (EasyStringUtil.isBlank(label)) {
            label = roleName;
        }
        Map<String, Object> mapRole = new ParamMap().add("roleName", roleName)
                .add("label", label).add("createdUserId", AppUser.getCurrentUser(request).getId()).toMap();
        roleService.addRole(mapRole);
        model.addAttribute("role", mapRole);
        return "/admin/role/list-tr";
    }

    @RequestMapping("/admin/role/{id}/delete")
    @ResponseBody
    public Boolean deleteAction(@PathVariable Integer id) {
        int nums = roleService.deleteRoleById(id);
        return nums > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @GetMapping("/admin/role/{id}")
    public String viewRolePage(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        model.addAttribute("dataReadOnly", "true");
        return "/admin/role/role-modal";
    }

    @GetMapping("/admin/role/{id}/edit")
    public String editRolePage(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        return "/admin/role/role-modal";
    }

    @PostMapping("/admin/role/{id}/update")
    public String updateRoleAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Map<String, Object> mapRole = roleService.getRoleMapById(id);
        Map<String, Object> mapForUpdate = ParamMap.toUpdateDataMap(request, mapRole);
        if (MapUtil.isNotEmpty(mapForUpdate)) {
            mapForUpdate.put("id", id);
            boolean updateOk = roleService.updateRoleMap(mapForUpdate);
            if (updateOk) {
                mapForUpdate.forEach(mapRole::put);
            }
        }
        model.addAttribute("role", mapRole);
        return "/admin/role/list-tr";
    }

    @GetMapping("/admin/role/{id}/permissions")
    public String rolePermissionPage(@PathVariable Integer id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        model.addAttribute("permissions", permissionDao.findAll());
        return "/admin/role/permissons-modal";
    }

    @PostMapping("/admin/role/{id}/permissions")
    public String rolePermissionAction(@PathVariable Integer id,
                                       @RequestParam(value = "permissions[]", defaultValue = "") String[] permissionKeys,
                                       Model model) {

        roleService.updateRolePermissions(id, permissionKeys);
        model.addAttribute("role", roleService.getRoleById(id));
        return "/admin/role/list-tr";
    }
}
