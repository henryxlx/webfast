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

    Map<String, Object> getCategory(Integer id);

    List<Map<String, Object>> getCategoryTree(Object groupId);

    Set<Object> findCategoryChildrenIds(Object categoryId);

    boolean isCategoryCodeAvaliable(String code, String exclude);

    void updateCategory(AppUser currentUser, Integer id, Map<String, Object> category);

    void deleteCategory(AppUser currentUser, Integer id);

    public Map<String, Object> buildCategoryChoices(String groupCode);

    public Map<String, Object> buildCategoryChoices(String groupCode, String indent);

    Map<String, Object> addGroup(Map<String, Object> groupMap);

    Object getGroup(Object groupId);

    Map<String, Object> getGroupByCode(String code);
}
