package com.jetwinner.webfast.kernel.dao;

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
}
