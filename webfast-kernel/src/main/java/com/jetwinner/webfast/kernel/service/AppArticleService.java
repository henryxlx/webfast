package com.jetwinner.webfast.kernel.service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppArticleService {

    int searchArticlesCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchArticles(Map<String, Object> conditions, String sortName,
                                             Integer start, Integer limit);
}
