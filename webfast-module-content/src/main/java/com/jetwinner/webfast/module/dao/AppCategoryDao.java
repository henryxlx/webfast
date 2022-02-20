package com.jetwinner.webfast.module.dao;

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
}
