package com.jetwinner.webfast.module.bigapp.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppArticleCategoryDao {

    Map<String, Object> getCategory(Object id);

    List<Map<String, Object>> findCategoriesByIds(Set<Object> ids);

    List<Map<String, Object>> findAllCategories();

    Map<String, Object> addCategory(Map<String, Object> category);

    Map<String, Object> findCategoryByCode(String code);

    void updateCategory(Integer id, Map<String, Object> fields);

    int findCategoriesCountByParentId(Integer parentId);

    void deleteByIds(Set<Object> ids);
}
