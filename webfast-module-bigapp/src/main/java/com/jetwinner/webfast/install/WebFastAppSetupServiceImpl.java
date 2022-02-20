package com.jetwinner.webfast.install;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.service.*;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.service.AppCategoryService;
import com.jetwinner.webfast.module.bigapp.service.AppContentService;
import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xulixin
 */
@Primary
@Component
public class WebFastAppSetupServiceImpl extends FastAppSetupServiceImpl {

    private final AppContentService contentService;
    private final AppTagService tagService;
    private final AppCategoryService categoryService;

    public WebFastAppSetupServiceImpl(AppUserService userService,
                                      AppSettingService settingService,
                                      AppNavigationService navigationService,
                                      AppContentService contentService,
                                      AppBlockService blockService,
                                      AppTagService tagService,
                                      AppCategoryService categoryService,
                                      AppFileService fileService) {

        super(blockService, fileService, navigationService, settingService, userService);
        this.contentService = contentService;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @Override
    public void initTag() {
        Map<String, Object> defaultTag = tagService.getTagByName("默认标签");
        if (defaultTag == null || defaultTag.size() == 0) {
            tagService.addTag(new ParamMap().add("name", "默认标签").toMap());
        }
    }

    @Override
    public void initCategory(BaseAppUser user) {
        Map<String, Object> group = categoryService.addGroup(new ParamMap()
                .add("name", "课程分类")
                .add("code", "course")
                .add("depth", 2).toMap());

        categoryService.createCategory(toCurrentUser(user), new ParamMap()
                .add("name", "默认分类")
                .add("code", "default")
                .add("weight", 100)
                .add("groupId", group.get("id"))
                .add("parentId", 0).toMap());
    }

    @Override
    public void initPages(BaseAppUser user) {
        AppUser currentUser = toCurrentUser(user);
        ParamMap paramMap = new ParamMap()
                .add("title", "关于我们")
                .add("type", "page")
                .add("alias", "aboutus")
                .add("body", "")
                .add("template", "default")
                .add("status", "published")
                .add("userId", 3);
        contentService.createContent(currentUser, paramMap.toMap());

        paramMap = new ParamMap()
                .add("title", "常见问题")
                .add("type", "page")
                .add("alias", "questions")
                .add("body", "")
                .add("template", "default")
                .add("status", "published");
        contentService.createContent(currentUser, paramMap.toMap());
    }

    @Override
    public void initArticleSetting() {
        ParamMap setting = new ParamMap()
                .add("name", "资讯频道")
                .add("pageNums", 20);
        settingService.set("article", setting.toMap());
    }

    @Override
    public String getOtherSqlFilePath() {
        return "sql/mysql/webfast-module-content.sql";
    }
}
