package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.model.AppModelNavigation;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNavigationService {

    void createNavigation(AppUser currentUser, Map<String, Object> model);

    List<AppModelNavigation> getNavigationsListByType(String type);

    List<AppModelNavigation> findNavigationsByType(String type, int start, int limit);

    int deleteNavigation(Integer id);

    AppModelNavigation getNavigationById(Integer id);

    int updateNavigation(AppUser currentUser, Integer id, Map<String, Object> fields);

    List<AppModelNavigation> getNavigationsTreeByType(String type);

    int updateNavigationsSequenceByIds(List<Object> ids);
}
