package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.service.AppRoleService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminUserController")
public class UserController {

    private final AppUserService userService;
    private final AppRoleService roleService;

    public UserController(AppUserService userService, AppRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/admin/user")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> conditions = ParamMap.toConditionMap(request);
        Paginator paginator = new Paginator(request, userService.searchUserCount(conditions), 20);

        model.addAttribute("roles", roleService.listAllRole());
        model.addAttribute("users", userService.searchUsers(conditions,
                OrderBy.builder().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()));
        model.addAttribute("paginator", paginator);
        return "/admin/user/index";
    }

    @GetMapping("/admin/user/create")
    public String createUserPage() {
        return "/admin/user/create-modal";
    }

    @RequestMapping("/admin/user/create-email-check")
    @ResponseBody
    public Map<String, Object> checkEmailAction(String value) {
        Map<String, Object> map = new HashMap<>(2);
        if ("admin@hotmail!com".equals(value)) {
            map.put("success", Boolean.FALSE);
            map.put("message", "该Email地址已经被使用，请重新修改！");
        } else {
            map.put("success", Boolean.TRUE);
            map.put("message", "该Email地址可以使用");
        }
        return map;
    }

    @RequestMapping("/admin/user/create-nickname-check")
    @ResponseBody
    public Map<String, Object> nicknameCheckAction(String value) {
        // list($result, $message) = $this->getAuthService()->checkUsername($nickname);
        Map<String, Object> map = new HashMap<>(2);
        if ("admin".equals(value)) {
            map.put("success", Boolean.FALSE);
            map.put("message", "该昵称已存在，不能被使用");
        } else {
            map.put("success", Boolean.TRUE);
            map.put("message", "该昵称可以使用");
        }
        return map;
    }

    @GetMapping("/admin/user/{id}")
    public String showUserPage(@PathVariable Integer id, Model model) {
        AppUser user = userService.getUser(id);
        Map<String, Object> profile = userService.getUserProfile(id);
        profile.put("title", user.getTitle());
        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listAllRole());
        // model.addAttribute("fields", getFields());
        return "/admin/user/show-modal";
    }
}
