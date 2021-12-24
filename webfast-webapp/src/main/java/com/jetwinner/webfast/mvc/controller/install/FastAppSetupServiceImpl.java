package com.jetwinner.webfast.mvc.controller.install;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class FastAppSetupServiceImpl {

    private AppUserService userService;
    private UserAccessControlService userAccessControlService;
    private AppSettingService settingService;

    public FastAppSetupServiceImpl(AppUserService userService,
                                   UserAccessControlService userAccessControlService,
                                   AppSettingService settingService) {

        this.userService = userService;
        this.userAccessControlService = userAccessControlService;
        this.settingService = settingService;
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

    public void initSiteSettings(Map<String, String> params) {
        ParamMap defaultSetting = new ParamMap()
                .add("name", params.get("sitename"))
                .add("slogan","")
                .add("url","")
                .add("logo","")
                .add("seo_keywords","")
                .add("seo_description","")
                .add("master_email", params.get("email"))
                .add("icp","")
                .add("analytics","")
                .add("status","open")
                .add("closed_note","")
                .add("homepage_template","less");
        settingService.set("site", defaultSetting.toMap());
    }

    public void initRegisterSetting(Object admin) {
    }

    public void initMailerSetting(String siteName) {
        ParamMap defaultSetting = new ParamMap()
                .add("enabled", 0)
                .add("host", "smtp.example.com")
                .add("port", "25")
                .add("username", "user@example.com")
                .add("password", "")
                .add("from", "user@example.com")
                .add("name",  siteName);
        settingService.set("mailer", defaultSetting.toMap());
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
