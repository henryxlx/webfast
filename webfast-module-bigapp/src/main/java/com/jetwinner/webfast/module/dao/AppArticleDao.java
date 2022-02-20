package com.jetwinner.webfast.module.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppArticleDao {

    int searchArticlesCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchArticles(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                             Integer start, Integer limit);

    Map<String, Object> addArticle(Map<String, Object> article);
}
