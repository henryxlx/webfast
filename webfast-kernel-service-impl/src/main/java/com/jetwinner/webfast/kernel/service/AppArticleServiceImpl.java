package com.jetwinner.webfast.kernel.service;

import com.jetwinner.toolbag.MapKitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.dao.AppArticleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Service
public class AppArticleServiceImpl implements AppArticleService {

    private final AppArticleDao articleDao;
    private final AppArticleCategoryService categoryService;

    public AppArticleServiceImpl(AppArticleDao articleDao, AppArticleCategoryService categoryService) {
        this.articleDao = articleDao;
        this.categoryService = categoryService;
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

    private void prepareSearchConditions(Map<String, Object> conditions) {
        MapKitOnJava8.filter(conditions);

        if (EasyStringUtil.isNotBlank(conditions.get("includeChildren")) && conditions.containsKey("categoryId")) {
            Set<Object> childrenIds = categoryService.findCategoryChildrenIds(conditions.get("categoryId"));
            conditions.put("categoryIds", childrenIds.add(conditions.get("categoryId")));
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
