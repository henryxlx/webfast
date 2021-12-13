package com.jetwinner.webfast.mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminUserController")
public class UserController {

    @RequestMapping("/admin/user")
    public String indexPage() {
        return "/admin/user/index";
    }

    @GetMapping("/admin/user/create")
    public String createUserPage() {
        return "/admin/user/create-modal";
    }

    @RequestMapping("/admin/user/create-email-check")
    @ResponseBody
    public Map<String, Object> checkEmailAction(String value) {
        Map<String, Object> map = new HashMap<>();
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
        Map<String, Object> map = new HashMap<>();
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
