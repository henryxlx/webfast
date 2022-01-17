package com.jetwinner.webfast.mvc.controller.install;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.*;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class FastAppSetupServiceImpl {

    private final AppUserService userService;
    private final UserAccessControlService userAccessControlService;
    private final AppSettingService settingService;
    private final AppNavigationService navigationService;
    private final AppContentService contentService;
    private final AppBlockService blockService;

    public FastAppSetupServiceImpl(AppUserService userService,
                                   UserAccessControlService userAccessControlService,
                                   AppSettingService settingService,
                                   AppNavigationService navigationService,
                                   AppContentService contentService,
                                   AppBlockService blockService) {

        this.userService = userService;
        this.userAccessControlService = userAccessControlService;
        this.settingService = settingService;
        this.navigationService = navigationService;
        this.contentService = contentService;
        this.blockService = blockService;
    }

    public void initAdmin(Map<String, String> params) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.putAll(params);
        try {
            Map<String, Object> user = MapUtil.newHashMap();
            BaseAppUser appUser = new BaseAppUser();
            appUser.setPassword(params.get("password"));
            userAccessControlService.setEncryptPassword(appUser);
            user.put("password", appUser.getPassword());
            user.put("salt", appUser.getSalt());
            user.put("email", params.get("email"));
            user.put("username", params.get("username"));
            user.put("setup", (0));
            user.put("roles", "ROLE_USER|ROLE_TEACHER|ROLE_SUPER_ADMIN|ROLE_ADMIN");
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
                .add("slogan", "")
                .add("url", "")
                .add("logo", "")
                .add("seo_keywords", "")
                .add("seo_description", "")
                .add("master_email", params.get("email"))
                .add("icp", "")
                .add("analytics", "")
                .add("status", "open")
                .add("closed_note", "")
                .add("homepage_template", "less");
        settingService.set("site", defaultSetting.toMap());
    }

    public void initRegisterSetting(Map<String, String> params) {
        String emailBody = "Hi, {{nickname}}" + "\n" +
                "\n" +
                "欢迎加入{{sitename}}!" + "\n" +
                "\n" +
                "请点击下面的链接完成注册：" + "\n" +
                "\n" +
                "{{verifyurl}}" + "\n" +
                "\n" +
                "如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏中打开，该链接地址24小时内打开有效。" + "\n" +
                "\n" +
                "感谢对{{sitename}}的支持！" + "\n" +
                "\n" +
                "{{sitename}} {{siteurl}}" + "\n" +
                "\n" +
                "(这是一封自动产生的email，请勿回复。)" + "\n";
        ParamMap defaultSetting = new ParamMap()
                .add("register_mode", "opened")
                .add("email_activation_title", "请激活您的{{sitename}}账号")
                .add("email_activation_body", emailBody)
                .add("welcome_enabled", "opened")
                .add("welcome_sender", params.get("username"))
                .add("welcome_methods", "")
                .add("welcome_title", "欢迎加入{{sitename}}")
                .add("welcome_body", "您好{{username}}，我是{{sitename}}的管理员，欢迎加入{{sitename}}，祝您学习愉快。如有问题，随时与我联系。");

        settingService.set("auth", defaultSetting.toMap());
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
        ParamMap defaultSetting = new ParamMap()
                .add("upload_mode", "local")
                .add("cloud_access_key", "")
                .add("cloud_secret_key", "")
                .add("cloud_api_server", "https://api.webfast.com")
                .add("cloud_bucket", "");

        settingService.set("storage", defaultSetting.toMap());
    }

    public void initFile() {
    }

    public void initPages(BaseAppUser user) {
        ParamMap paramMap = new ParamMap()
                .add("title", "关于我们")
                .add("type", "page")
                .add("alias", "aboutus")
                .add("body", "")
                .add("template", "default")
                .add("status", "published")
                .add("userId", 3);
        contentService.createContent(paramMap.toMap(), user);

        paramMap.add("title", "常见问题")
                .add("type", "page")
                .add("alias", "questions")
                .add("body", "")
                .add("template", "default")
                .add("status", "published");
        contentService.createContent(paramMap.toMap(), user);
    }

    public void initNavigations() {
        ParamMap paramMap = new ParamMap()
                .add("name", "师资力量")
                .add("url", "teacher")
                .add("sequence", 1)
                .add("isNewWin", 0)
                .add("isOpen", 1)
                .add("type", "top");
        navigationService.createNavigation(paramMap.toMap());

        paramMap.add("name", "常见问题")
                .add("url", "page/questions")
                .add("sequence", 2)
                .add("isNewWin", 0)
                .add("isOpen", 1)
                .add("type", "top");
        navigationService.createNavigation(paramMap.toMap());

        paramMap.add("name", "关于我们")
                .add("url", "page/aboutus")
                .add("sequence", 2)
                .add("isNewWin", 0)
                .add("isOpen", 1)
                .add("type", "top");
        navigationService.createNavigation(paramMap.toMap());
    }

    public void initBlocks(BaseAppUser user) {
        String content = "<a href=\"\"><img src=\"assets/img/placeholder/carousel-1200x256-1.png\" /></a>" +
                "\n" +
                "<a href=\"#\"><img src=\"assets/img/placeholder/carousel-1200x256-2.png\" /></a>" +
                "\n" +
                "<a href=\"#\"><img src=\"assets/img/placeholder/carousel-1200x256-3.png\" /></a>" +
                "\n";
        Integer id = blockService.createBlock(new ParamMap()
                .add("code", "home_top_banner")
                .add("title", "默认主题：首页头部图片轮播").toMap(), user);
        blockService.updateContent(id, content);
    }

    public void initThemes() {
        settingService.set("theme", new ParamMap().add("uri", "default").toMap());
    }

    public void initLockFile() {
    }

    public void initArticleSetting() {
        ParamMap setting = new ParamMap()
                .add("name", "资讯频道")
                .add("pageNums", 20);
        settingService.set("article", setting.toMap());
    }

    public BaseAppUser getUserByUsername(String username) {
        return userService.getBaseAppUserByUsername(username);
    }
}
