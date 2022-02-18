package com.jetwinner.webfast.module.service.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.webfast.module.dao.AppCategoryDao;
import com.jetwinner.webfast.module.service.AppCategoryService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    private final AppCategoryDao categoryDao;

    public AppCategoryServiceImpl(AppCategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Map<String, Map<String, Object>> findCategoriesByIds(Set<Object> categoryIds) {
        return ArrayToolkit.index(categoryDao.findByIds(categoryIds), "id");
    }

    @Override
    public Map<String, Object> addGroup(Map<String, Object> groupMap) {
        return null;
    }

    @Override
    public void createCategory(Map<String, Object> categoryMap) {

    }
}
