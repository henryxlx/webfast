package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.ArrayToolkit;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    public UserController(AppUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin/user")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> conditions = ArrayToolkit.toConditionMap(request);
        Paginator paginator = new Paginator(request, userService.searchUserCount(conditions), 20);

        model.addAttribute("users", userService.searchUsers(
                conditions,
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
}
