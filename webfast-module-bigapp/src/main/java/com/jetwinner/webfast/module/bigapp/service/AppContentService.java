package com.jetwinner.webfast.module.bigapp.service;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentService {

    void createContent(AppUser currentUser, Map<String, Object> model);

    int searchContentCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchContents(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                             Integer start, Integer limit);

    Map<String, Object> getContent(Integer id);

    void trashContent(AppUser currentUser, Integer id);

    void publishContent(AppUser currentUser, Integer id);

    Map<String, Object> getContentByAlias(String alias);
}
