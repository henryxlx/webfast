package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.model.AppModelNavigation;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNavigationService {

    void createNavigation(Map<String, Object> model);

    List<AppModelNavigation> getNavigationsListByType(String type);

    List<AppModelNavigation> findNavigationsByType(String type, int start, int limit);

    int deleteNavigation(Integer id);

    AppModelNavigation getNavigationById(Integer id);

    int updateNavigation(Integer id, Map<String, Object> fields);
}
