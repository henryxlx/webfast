package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.service.AppUserFieldService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import com.jetwinner.webfast.session.FlashMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastSiteSettingController")
@RequestMapping(SettingsController.VIEW_PATH)
public class SettingsController {

    static final String VIEW_PATH ="/settings";

    private final AppUserService userService;
    private final AppUserFieldService userFieldService;

    public SettingsController(AppUserService userService, AppUserFieldService userFieldService) {
        this.userService = userService;
        this.userFieldService = userFieldService;
    }

    @RequestMapping("")
    public String profilePage(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
        Map<String, Object> profile = userService.getUserProfile(user.getId());
        profile.put("title", user.getTitle());

        if ("POST".equals(request.getMethod())) {
            Map<String, Object> mapForUpdate = ParamMap.toUpdateDataMap(request.getParameterMap(), profile);
            if (mapForUpdate != null && mapForUpdate.size() > 0) {
                if (!(StringUtils.hasLength(user.getVerifiedMobile()) &&
                        EasyStringUtil.isNotBlank(profile.get("mobile")))) {

                    userService.updateUserProfile(user.getId(), mapForUpdate);
                    FlashMessageUtil.setFlashMessage("success", "基础信息保存成功。", request.getSession());
                } else {
                    FlashMessageUtil.setFlashMessage("danger", "不能修改已绑定的手机。", request.getSession());
                }
            } else {
                FlashMessageUtil.setFlashMessage("danger", "没有可更新的数据。", request.getSession());
            }
            return "redirect:/settings";
        }

        List<Map<String, Object>> fields = userFieldService.getAllFieldsOrderBySeqAndEnabled();
        String[][] fieldDataTypeMappings = {{"textField", "text"}, {"varcharField", "varchar"},
                {"intField", "int"}, {"floatField", "float"}, {"dateField", "date"}};
        fields.forEach(field -> {
            for (String[] dataTypeMapping : fieldDataTypeMappings) {
                if (dataTypeMapping[0].equals(field.get("fieldName"))) {
                    field.put("type", dataTypeMapping[1]);
                }
            }
        });
        model.addAttribute("fields", fields);

        if (profile.containsKey("idcard") && "0".equals(profile.get("idcard"))) {
            profile.put("idcard", "");
        }
        model.addAttribute("profile", profile);
        model.addAttribute("fromCourse", request.getParameter("fromCourse"));
        model.addAttribute("user", user);
        return VIEW_PATH + "/profile";
    }

    @RequestMapping("/avatar")
    public String avatarPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", BaseControllerHelper.getCurrentUser(request));
        return VIEW_PATH + "/avatar";
    }

    @RequestMapping("/security")
    public String securityPage() {
        return VIEW_PATH + "/security";
    }

    @RequestMapping("/email")
    public String emailPage() {
        return VIEW_PATH + "/email";
    }

    @RequestMapping("/setup")
    public String setupPage() {
        return VIEW_PATH + "/setup";
    }
}
