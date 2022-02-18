package com.jetwinner.webfast.install;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.*;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.service.AppCategoryService;
import com.jetwinner.webfast.module.service.AppContentService;
import com.jetwinner.webfast.module.service.AppTagService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xulixin
 */
@Primary
@Component
public class WebFastAppSetupServiceImpl implements FastAppSetupService {

    private final AppUserService userService;
    private final AppSettingService settingService;
    private final AppNavigationService navigationService;
    private final AppContentService contentService;
    private final AppBlockService blockService;
    private final AppTagService tagService;
    private final AppCategoryService categoryService;
    private final AppFileService fileService;

    public WebFastAppSetupServiceImpl(AppUserService userService,
                                      AppSettingService settingService,
                                      AppNavigationService navigationService,
                                      AppContentService contentService,
                                      AppBlockService blockService,
                                      AppTagService tagService,
                                      AppCategoryService categoryService,
                                      AppFileService fileService) {

        this.userService = userService;
        this.settingService = settingService;
        this.navigationService = navigationService;
        this.contentService = contentService;
        this.blockService = blockService;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    @Override
    public void initAdmin(Map<String, String> params) {
        Map<String, Object> user = MapUtil.newHashMap();
        user.putAll(params);
        try {
            user.put("roles", "ROLE_USER|ROLE_TEACHER|ROLE_SUPER_ADMIN|ROLE_ADMIN");
            user.put("createdIp", "127.0.0.1");
            userService.register(user);
        } catch (Exception e) {
            throw new RuntimeGoingException(e.getMessage());
        }
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void initStorageSetting() {
        ParamMap defaultSetting = new ParamMap()
                .add("upload_mode", "local")
                .add("cloud_access_key", "")
                .add("cloud_secret_key", "")
                .add("cloud_api_server", "https://api.webfast.com")
                .add("cloud_bucket", "");

        settingService.set("storage", defaultSetting.toMap());
    }

    @Override
    public void initTag() {
        Map<String, Object> defaultTag = tagService.getTagByName("默认标签");
        if (defaultTag == null || defaultTag.size() == 0) {
            tagService.addTag(new ParamMap().add("name", "默认标签").toMap());
        }
    }

    @Override
    public void initCategory() {
        Map<String, Object> group = categoryService.addGroup(new ParamMap()
                .add("name", "课程分类")
                .add("code", "course")
                .add("depth", 2).toMap());

        categoryService.createCategory(new ParamMap()
                .add("name", "默认分类")
                .add("code", "default")
                .add("weight", 100)
                .add("groupId", group.get("id"))
                .add("parentId", 0).toMap());
    }

    @Override
    public void initFile() {
        fileService.addFileGroup(new ParamMap()
                .add("name", "默认文件组")
                .add("code", "default")
                .add("public", 1).toMap());

        fileService.addFileGroup(new ParamMap()
                .add("name", "缩略图")
                .add("code", "thumb")
                .add("public", 1).toMap());

        fileService.addFileGroup(new ParamMap()
                .add("name", "课程")
                .add("code", "course")
                .add("public", 1).toMap());

        fileService.addFileGroup(new ParamMap()
                .add("name", "用户")
                .add("code", "user")
                .add("public", 1).toMap());

        fileService.addFileGroup(new ParamMap()
                .add("name", "课程私有文件")
                .add("code", "course_private")
                .add("public", 0).toMap());

        fileService.addFileGroup(new ParamMap()
                .add("name", "资讯")
                .add("code", "article")
                .add("public", 1).toMap());
    }

    @Override
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

    @Override
    public void initNavigations(BaseAppUser user) {
        AppUser currentUser = new AppUser();
        currentUser.setId(user.getId());
        currentUser.setUsername(user.getUsername());
        currentUser.setLoginIp("::");
        ParamMap paramMap = new ParamMap()
                .add("name", "师资力量")
                .add("url", "teacher")
                .add("sequence", 1)
                .add("isNewWin", 0)
                .add("isOpen", 1)
                .add("type", "top");
        navigationService.createNavigation(currentUser, paramMap.toMap());

        paramMap.add("name", "常见问题")
                .add("url", "page/questions")
                .add("sequence", 2)
                .add("isNewWin", 0)
                .add("isOpen", 1)
                .add("type", "top");
        navigationService.createNavigation(currentUser, paramMap.toMap());

        paramMap.add("name", "关于我们")
                .add("url", "page/aboutus")
                .add("sequence", 2)
                .add("isNewWin", 0)
                .add("isOpen", 1)
                .add("type", "top");
        navigationService.createNavigation(currentUser, paramMap.toMap());
    }

    @Override
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

    @Override
    public void initThemes() {
        settingService.set("theme", new ParamMap().add("uri", "default").toMap());
    }

    @Override
    public void initLockFile() {
    }

    @Override
    public void initArticleSetting() {
        ParamMap setting = new ParamMap()
                .add("name", "资讯频道")
                .add("pageNums", 20);
        settingService.set("article", setting.toMap());
    }

    @Override
    public BaseAppUser getUserByUsername(String username) {
        return userService.getBaseAppUserByUsername(username);
    }

    @Override
    public String getOtherSqlFilePath() {
        return "sql/mysql/webfast-module-content.sql";
    }
}
