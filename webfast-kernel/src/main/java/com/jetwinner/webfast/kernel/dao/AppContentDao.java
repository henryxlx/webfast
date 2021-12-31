package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderBy;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentDao {

    void insert(Map<String, Object> model);

    int searchContentCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchContent(Map<String, Object> conditions,
                                            OrderBy[] orderByArray,
                                            Integer start, Integer limit);

    Map<String, Object> getContent(Integer id);

    void updateContent(Integer id, Map<String, Object> fields);

    Map<String, Object> getContentByAlias(String alias);
}