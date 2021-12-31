package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.ArrayTookKit;
import com.jetwinner.webfast.kernel.dao.AppCategoryDao;
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
        return ArrayTookKit.toMapForFreeMarkerView(categoryDao.findByIds(categoryIds), "id");
    }
}
