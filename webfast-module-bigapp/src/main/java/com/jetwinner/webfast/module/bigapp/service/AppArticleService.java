package com.jetwinner.webfast.module.bigapp.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppArticleService {

    int searchArticlesCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchArticles(Map<String, Object> conditions, String sortName,
                                             Integer start, Integer limit);

    void createArticle(AppUser currentUser, Map<String, Object> article);

    Map<String, Object> getArticle(Object id);

    Map<String, Object> getArticlePrevious(Object id);

    Map<String, Object> getArticleNext(Object id);

    void hitArticle(Object id);

    void updateArticle(AppUser currentUser, Integer id, Map<String, Object> article);

    int setArticleProperty(AppUser currentUser, Integer id, String propertyName);

    int cancelArticleProperty(AppUser currentUser, Integer id, String propertyName);

    int trashArticle(AppUser currentUser, Integer id);

    int deleteArticlesByIds(AppUser currentUser, String[] ids);

    int publishArticle(AppUser currentUser, Integer id);

    int unpublishArticle(AppUser currentUser, Integer id);
}
