package com.jetwinner.webfast.module.bigapp.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.MapKitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.FastTimeUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.dao.AppArticleDao;
import com.jetwinner.webfast.module.bigapp.service.AppArticleCategoryService;
import com.jetwinner.webfast.module.bigapp.service.AppArticleService;
import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Service
public class AppArticleServiceImpl implements AppArticleService {

    private final AppArticleDao articleDao;
    private final AppArticleCategoryService categoryService;
    private final AppTagService tagService;
    private final AppLogService logService;

    public AppArticleServiceImpl(AppArticleDao articleDao,
                                 AppArticleCategoryService categoryService,
                                 AppTagService tagService, AppLogService logService) {

        this.articleDao = articleDao;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.logService = logService;
    }

    @Override
    public int searchArticlesCount(Map<String, Object> conditions) {
        prepareSearchConditions(conditions);
        return articleDao.searchArticlesCount(conditions);
    }

    @Override
    public List<Map<String, Object>> searchArticles(Map<String, Object> conditions, String sortName,
                                                    Integer start, Integer limit) {

        OrderBy orderBy = filterSort(sortName);
        prepareSearchConditions(conditions);
        return articleDao.searchArticles(conditions, orderBy, start, limit);
    }

    @Override
    public void createArticle(AppUser currentUser, Map<String, Object> article) {
        if(article == null || article.size() == 0){
            throw new RuntimeGoingException("文章内容为空，创建文章失败！");
        }

        article = filterArticleFields(currentUser, article, false);
        article = articleDao.addArticle(article);
        logService.info(currentUser, "article", "create",
                String.format("创建文章《(%s)》(%s)", article.get("title"), article.get("id")));
    }

    @Override
    public Map<String, Object> getArticle(Object id) {
        return articleDao.getArticle(id);
    }

    @Override
    public Map<String, Object> getArticlePrevious(Object id) {
        Map<String, Object> article = getArticle(id);
        if(article.isEmpty()){
            throw new RuntimeGoingException("文章不存在，操作失败。");
        }
        Long createdTime = ValueParser.toLong(article.get("createdTime"));
        Object categoryId = article.get("categoryId");
        Map<String, Object> category = categoryService.getCategory(categoryId);
        if(category == null || category.isEmpty()){
            throw new RuntimeGoingException("文章分类不存在,操作失败！");
        }

        return articleDao.getArticlePrevious(categoryId, createdTime);
    }

    @Override
    public Map<String, Object> getArticleNext(Object id) {
        Map<String, Object> article = getArticle(id);
        if(article.isEmpty()){
            throw new RuntimeGoingException("文章不存在，操作失败。");
        }
        Long createdTime = ValueParser.toLong(article.get("createdTime"));
        Object categoryId = article.get("categoryId");
        Map<String, Object> category = categoryService.getCategory(categoryId);
        if(category == null || category.isEmpty()){
            throw new RuntimeGoingException("文章分类不存在,操作失败！");
        }

        return articleDao.getArticleNext(categoryId, createdTime);
    }

    @Override
    public void hitArticle(Object id) {
        Map<String, Object> checkArticle = getArticle(id);
        if(checkArticle.isEmpty()){
            throw new RuntimeGoingException("文章不存在，操作失败。");
        }

        articleDao.waveArticle(id, 1);
    }

    @Override
    public void updateArticle(AppUser currentUser, Integer id, Map<String, Object> article) {
        article = filterArticleFields(currentUser, article, true);

        int nums = articleDao.updateArticle(id, article);
        if (nums > 0) {
            article = getArticle(id);
            logService.info(currentUser, "Article", "update",
                    String.format("修改文章《(%s)》(%s)", article.get("title"), article.get("id")));
        }
    }

    @Override
    public int setArticleProperty(AppUser currentUser, Integer id, String propertyName) {
        Map<String, Object> article = getArticle(id);
        if(EasyStringUtil.isBlank(propertyName) || !article.containsKey(propertyName)){
            throw new RuntimeGoingException("属性{$property}不存在，更新失败！");
        }

        Map<String, Object> fields = new ParamMap().add(propertyName, 1).toMap();
        int nums = articleDao.updateArticle(id, fields);
        if (nums > 0) {
            logService.info(currentUser, "setArticleProperty", "updateArticleProperty",
                    String.format("文章#%d,%s=>%d", id, article.get(propertyName), 1));
        }
        return nums;
    }

    @Override
    public int cancelArticleProperty(AppUser currentUser, Integer id, String propertyName) {
        Map<String, Object> article = getArticle(id);
        if(EasyStringUtil.isBlank(propertyName) || !article.containsKey(propertyName)){
            throw new RuntimeGoingException("属性{$property}不存在，更新失败！");
        }

        Map<String, Object> fields = new ParamMap().add(propertyName, 0).toMap();
        int nums = articleDao.updateArticle(id, fields);
        if (nums > 0) {
            logService.info(currentUser, "cancelArticleProperty", "updateArticleProperty",
                    String.format("文章#%d,%s=>%d", id, article.get(propertyName), 1));
        }
        return nums;
    }

    @Override
    public int trashArticle(AppUser currentUser, Integer id) {
        Map<String, Object> checkArticle = getArticle(id);
        if(checkArticle == null || checkArticle.isEmpty()){
            throw new RuntimeGoingException("文章不存在，操作失败。");
        }

        int nums = articleDao.updateArticle(id, new ParamMap().add("status", "trash").toMap());
        if (nums > 0) {
            logService.info(currentUser, "Article", "trash", String.format("文章#%d移动到回收站", id));
        }
        return nums;
    }

    @Override
    public int deleteArticlesByIds(AppUser currentUser, String[] ids) {
        int nums = 0;
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                nums += deleteArticle(currentUser, id);
            }
        }
        return nums;
    }

    public int deleteArticle(AppUser currentUser, Object id) {
        Map<String, Object> checkArticle = getArticle(id);
        if(checkArticle == null || checkArticle.isEmpty()){
            throw new RuntimeGoingException("文章不存在，操作失败。");
        }

        int nums = articleDao.deleteArticle(id);
        if (nums > 0) {
            logService.info(currentUser, "Article", "delete", String.format("文章#%s永久删除", id));
        }
        return nums;
    }

    @Override
    public int publishArticle(AppUser currentUser, Integer id) {
        int nums = articleDao.updateArticle(id, new ParamMap().add("status", "published").toMap());
        if (nums > 0) {
            logService.info(currentUser, "Article", "publish", String.format("文章#%d发布", id));
        }
        return nums;
    }

    @Override
    public int unpublishArticle(AppUser currentUser, Integer id) {
        int nums = articleDao.updateArticle(id, new ParamMap().add("status", "unpublished").toMap());
        if (nums > 0) {
            logService.info(currentUser, "Article", "unpublish", String.format("文章#%d未发布", id));
        }
        return nums;
    }

    private Map<String, Object> filterArticleFields(AppUser currentUser, Map<String, Object> fields, boolean updateMode) {
        Map<String, Object> article = new HashMap<>(fields.size());

        Matcher m = Pattern.compile("<\\s*img(.+?)src=[\"'](.*?)[\"']\\s*/?\\s*>").matcher(String.valueOf(fields.get("body")));
        article.put("picture", m.find() ? m.group(0) : "");

        article.put("thumb", fields.get("thumb"));
        article.put("originalThumb", fields.get("originalThumb"));
        article.put("title", fields.get("title"));
        article.put("body", fields.get("body"));
        article.put("featured", EasyStringUtil.isBlank(fields.get("featured")) ? 0 : 1);
        article.put("promoted", EasyStringUtil.isBlank(fields.get("promoted")) ? 0 : 1);
        article.put("sticky", EasyStringUtil.isBlank(fields.get("sticky")) ? 0 : 1);
        article.put("categoryId", fields.get("categoryId"));
        article.put("source", fields.get("source"));
        article.put("sourceUrl", fields.get("sourceUrl"));
        article.put("publishedTime", FastTimeUtil.dateStrToLong(fields.get("publishedTime")));
        article.put("updatedTime", System.currentTimeMillis());

        if (EasyStringUtil.isNotBlank(fields.get("tags")) && !fields.get("tags").getClass().isArray()) {
            String[] arrayTags = EasyStringUtil.explode(",", fields.get("tags"));
            fields.put("tags", arrayTags);
            article.put("tagIds", ArrayToolkit.column(tagService.findTagsByNames(arrayTags), "id"));
        }

        if (!updateMode) {
            String[] arrayTags = ArrayToolkit.isArray(fields.get("tags")) ? (String[]) fields.get("tags") :
                    new String[]{String.valueOf(fields.get("tags"))};
            article.put("tagIds", ArrayToolkit.column(tagService.findTagsByNames(arrayTags), "id"));
            article.put("status", "published");
            article.put("userId", currentUser.getId());
            article.put("createdTime", System.currentTimeMillis());
        }

        return article;
    }

    private void prepareSearchConditions(Map<String, Object> conditions) {
        MapKitOnJava8.filter(conditions);

        if (EasyStringUtil.isNotBlank(conditions.get("includeChildren")) && conditions.containsKey("categoryId")) {
            Set<Object> childrenIds = categoryService.findCategoryChildrenIds(conditions.get("categoryId"));
            childrenIds.add(conditions.get("categoryId"));
            conditions.put("categoryIds", childrenIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
            conditions.remove("categoryId");
            conditions.remove("includeChildren");
        }
    }

    private OrderBy filterSort(String sort) {
        OrderBy orderBy;
        switch (sort) {
            case "created":
                orderBy = new OrderBy().addDesc("createdTime");
                break;
            case "published":
                orderBy = new OrderBy().addDesc("sticky").addDesc("publishedTime");
                break;
            case "normal":
                orderBy = new OrderBy().addDesc("publishedTime");
                break;
            case "popular":
                orderBy = new OrderBy().addDesc("hits");
                break;
            default:
                throw new RuntimeGoingException("参数sort不正确。");
        }
        return orderBy;
    }
}
