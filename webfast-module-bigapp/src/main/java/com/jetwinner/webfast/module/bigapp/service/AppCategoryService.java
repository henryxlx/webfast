package com.jetwinner.webfast.module.bigapp.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppCategoryService {

    Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds);

    void createCategory(AppUser currentUser, Map<String, Object> categoryMap);

    List<Map<String, Object>> getCategoryTree(Object groupId);

    Set<Object> findCategoryChildrenIds(Object categoryId);

    Map<String, Object> addGroup(Map<String, Object> groupMap);

    Map<String, Object> getGroupByCode(String code);
}