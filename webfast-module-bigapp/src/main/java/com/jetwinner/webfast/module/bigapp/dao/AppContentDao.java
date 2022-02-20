package com.jetwinner.webfast.module.bigapp.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentDao {

    void insert(Map<String, Object> model);

    int searchContentCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchContent(Map<String, Object> conditions,
                                            OrderByBuilder orderByBuilder,
                                            Integer start, Integer limit);

    Map<String, Object> getContent(Integer id);

    void updateContent(Integer id, Map<String, Object> fields);

    Map<String, Object> getContentByAlias(String alias);
}