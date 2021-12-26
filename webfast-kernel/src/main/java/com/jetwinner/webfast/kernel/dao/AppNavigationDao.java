package com.jetwinner.webfast.kernel.dao;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNavigationDao {

    void insert(Map<String, Object> model);

    Integer getNavigationsCountByType(String type);
}
