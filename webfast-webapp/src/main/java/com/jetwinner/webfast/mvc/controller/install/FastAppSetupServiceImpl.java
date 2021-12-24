package com.jetwinner.webfast.mvc.controller.install;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class FastAppSetupServiceImpl {

    private AppUserService userService;
    private UserAccessControlService userAccessControlService;

    public FastAppSetupServiceImpl(AppUserService userService,
                                   UserAccessControlService userAccessControlService) {

        this.userService = userService;
        this.userAccessControlService = userAccessControlService;
    }

    public void initAdmin(Map<String, String> params) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.putAll(params);
        try {
            Map<String, Object> user = MapUtil.newHashMap();
            AppUser appUser = new AppUser();
            appUser.setPassword(params.get("password"));
            userAccessControlService.setEncryptPassword(appUser);
            user.put("password", appUser.getPassword());
            user.put("salt", appUser.getSalt());
            user.put("email", params.get("email"));
            user.put("username", params.get("username"));
            user.put("setup", (0));
            user.put("roles", "ROLE_USER|ROLE_TEACHER|ROLE_SUPER_ADMIN|ROLE_ADMIN|ROLE_BACKEND");
            user.put("createdIp", "127.0.0.1");
            user.put("type", "default");
            userService.register(user);
        } catch (Exception e) {
            throw new RuntimeGoingException(e.getMessage());
        }
    }

    public void initSiteSettings() {

    }

    public void initRegisterSetting(Object admin) {
    }

    public void initMailerSetting(String siteName) {
    }

    public void initStorageSetting() {
    }

    public void initTag() {
    }

    public void initFile() {
    }

    public void initPages() {
    }

    public void initNavigations() {
    }

    public void initBlocks() {
    }

    public void initThemes() {
    }

    public void initLockFile() {
    }

    public void initArticleSetting() {
    }

    public void initDefaultSetting() {
    }
}
