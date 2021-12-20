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
            AppUser user = new AppUser();
            user.setPassword(params.get("password"));
            userAccessControlService.setEncryptPassword(user);
            user.setEmail(params.get("email"));
            user.setUsername(params.get("nickname"));
            user.setSetup(0);
            user.setRoles("ROLE_USER|ROLE_TEACHER|ROLE_SUPER_ADMIN");
            user.setCreatedIp("127.0.0.1");
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
