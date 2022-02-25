package com.jetwinner.webfast.module.bigapp.dao;

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

    Map<String, Object> getArticle(Object id);

    Map<String, Object> getArticlePrevious(Object categoryId, Long createdTime);

    Map<String, Object> getArticleNext(Object categoryId, Long createdTime);

    int waveArticle(Object id, int diff);

    int updateArticle(Integer id, Map<String, Object> fields);
}
