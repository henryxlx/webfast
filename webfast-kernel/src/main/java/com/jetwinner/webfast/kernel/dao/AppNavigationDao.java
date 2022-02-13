package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.model.AppModelNavigation;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNavigationDao {

    int insert(Map<String, Object> model);

    Integer getNavigationsCountByType(String type);

    int countNavigationsByType(String type);

    List<AppModelNavigation> findAllByType(String type, int start, int count);

    int deleteById(Integer id);

    int deleteByParentId(Integer parentId);

    AppModelNavigation getById(Integer id);

    int updateNavigation(Map<String, Object> fields);
}
