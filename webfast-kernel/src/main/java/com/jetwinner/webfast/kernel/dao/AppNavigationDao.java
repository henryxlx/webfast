package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.model.AppModelNavigation;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNavigationDao {

    void insert(Map<String, Object> model);

    Integer getNavigationsCountByType(String type);

    int countNavigationsByType(String type);

    List<AppModelNavigation> findAllByType(String type, int start, int count);
}
