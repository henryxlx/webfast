package com.jetwinner.webfast.module.bigapp.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppArticleCategoryService {

    Map<String, Object> getCategory(Object id);

    Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds);

    List<Map<String, Object>> getCategoryTree();

    Set<Object> findCategoryChildrenIds(Object categoryId);

    void createCategory(Map<String, Object> formData);

    boolean isCategoryCodeAvaliable(String code, String exclude);

    Map<String, Object> getCategoryByCode(String code);

    void updateCategory(Integer id, Map<String, Object> fields);

    int findCategoriesCountByParentId(Integer parentId);

    void deleteCategory(Integer id);
}
