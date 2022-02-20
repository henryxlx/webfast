package com.jetwinner.webfast.module.bigapp.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppCategoryDao {

    List<Map<String, Object>> findByIds(Set<Object> ids);

    List<Map<String, Object>> findCategoriesByGroupId(Object groupId);

    Map<String, Object> getCategory(Object id);

    Map<String, Object> addCategory(Map<String, Object> fields);

    Map<String, Object> findCategoryByCode(String code);

    void updateCategory(Integer id, Map<String, Object> fields);

    void deleteByIds(Set<Object> ids);
}
