package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppPermissionDao;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminPermissionController")
public class PermissionController {

    private final AppPermissionDao permissionDao;

    public PermissionController(AppPermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @GetMapping("/admin/permission")
    public String indexPage(Model model) {
        model.addAttribute("permissions", permissionDao.findAll());
        return "/admin/permission/index";
    }

    @GetMapping("/admin/permission/create")
    public String createPermissionPage() {
        return "/admin/permission/permission-model";
    }

    @RequestMapping("/admin/permission/checkname")
    @ResponseBody
    public Map checkRoleNameAction(@RequestParam("value") String permissionKey, String exclude) {
        Map<String, Object> map = new HashMap<>(2);
        boolean available = permissionDao.isPermissionKeyAvailable(permissionKey, exclude);

        if (available) {
            map.put("success", Boolean.TRUE);
            map.put("message", "");
        } else {
            map.put("success", Boolean.FALSE);
            map.put("message", "权限标识关键字已存在");
        }
        return map;
    }

    @PostMapping("/admin/permission/create")
    public String createPermissionAction(String permissionKey, String label, HttpServletRequest request, Model model) {
        if (EasyStringUtil.isBlank(label)) {
            label = permissionKey;
        }
        Map<String, Object> mapPermission = new ParamMap().add("permissionKey", permissionKey)
                .add("label", label).add("createdUserId", AppUser.getCurrentUser(request).getId()).toMap();
        permissionDao.insert(mapPermission);
        model.addAttribute("perm", mapPermission);
        return "/admin/permission/list-tr";
    }

    @RequestMapping("/admin/permission/{id}/delete")
    @ResponseBody
    public Boolean deleteAction(@PathVariable Integer id) {
        int nums = permissionDao.delete(id);
        return nums > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @GetMapping("/admin/permission/{id}/edit")
    public String editPermissionPage(@PathVariable Integer id, Model model) {
        model.addAttribute("perm", permissionDao.get(id));
        return "/admin/permission/permission-model";
    }

    @PostMapping("/admin/permission/{id}/update")
    public String updatePermissionAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Map<String, Object> mapPermission = permissionDao.get(id);
        Map<String, Object> mapForNew = ParamMap.toUpdateDataMap(request.getParameterMap(), mapPermission);
        if (!mapForNew.isEmpty()) {
            mapForNew.put("id", id);
            permissionDao.update(mapForNew);
            mapForNew.forEach((k, v) -> {
                mapPermission.put(k, v);
            });
        }
        model.addAttribute("perm", mapPermission);
        return "/admin/permission/list-tr";
    }
}
