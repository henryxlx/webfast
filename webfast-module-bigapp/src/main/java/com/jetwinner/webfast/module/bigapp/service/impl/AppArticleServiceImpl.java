package com.jetwinner.webfast.module.bigapp.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.MapKitOnJava8;
import com.jetwinner.util.EasyDateUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.module.bigapp.dao.AppArticleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
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

        OrderByBuilder orderByBuilder = filterSort(sortName);
        prepareSearchConditions(conditions);
        return articleDao.searchArticles(conditions, orderByBuilder, start, limit);
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
        article.put("publishedTime", EasyDateUtil.toLongTime(fields.get("publishedTime")));
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
            conditions.put("categoryIds", childrenIds);
            conditions.remove("categoryId");
            conditions.remove("includeChildren");
        }
    }

    private OrderByBuilder filterSort(String sort) {
        OrderByBuilder orderByBuilder;
        switch (sort) {
            case "created":
                orderByBuilder = OrderBy.builder().addDesc("createdTime");
                break;
            case "published":
                orderByBuilder = OrderBy.builder().addDesc("sticky").addDesc("publishedTime");
                break;
            case "normal":
                orderByBuilder = OrderBy.builder().addDesc("publishedTime");
                break;
            case "popular":
                orderByBuilder = OrderBy.builder().addDesc("hits");
                break;
            default:
                throw new RuntimeGoingException("参数sort不正确。");
        }
        return orderByBuilder;
    }
}
