package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.BaseAppUser;
import com.jetwinner.webfast.kernel.typedef.ParamMap;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentService {

    void createContent(Map<String, Object> model, BaseAppUser currentUser);

    int searchContentCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchContents(Map<String, Object> conditions, ParamMap sort,
                                             Integer start, Integer limit);

    Map<String, Object> getContent(Integer id);

    void trashContent(Integer id);

    void publishContent(Integer id);
}
