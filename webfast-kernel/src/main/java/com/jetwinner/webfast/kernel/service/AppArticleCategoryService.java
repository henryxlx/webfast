package com.jetwinner.webfast.kernel.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppArticleCategoryService {

    Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds);

    List<Map<String, Object>> getCategoryTree();

    Set<Object> findCategoryChildrenIds(Object categoryId);

    void createCategory(Map<String, Object> formData);

    boolean isCategoryCodeAvaliable(String code, String exclude);

    Map<String, Object> getCategoryByCode(String code);
}
