package com.jetwinner.webfast.module.service;

import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppCategoryService {

    Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds);

    Map<String, Object> addGroup(Map<String, Object> groupMap);

    void createCategory(Map<String, Object> categoryMap);
}
