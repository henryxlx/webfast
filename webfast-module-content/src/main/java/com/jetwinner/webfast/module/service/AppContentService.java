package com.jetwinner.webfast.module.service;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentService {

    void createContent(Map<String, Object> model, BaseAppUser currentUser);

    int searchContentCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchContents(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                             Integer start, Integer limit);

    Map<String, Object> getContent(Integer id);

    void trashContent(Integer id);

    void publishContent(Integer id);

    Map<String, Object> getContentByAlias(String alias);
}