package com.jetwinner.webfast.module.dao;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppCategoryGroupDao {

    Map<String, Object> findGroupByCode(String code);

    Map<String, Object> getGroup(Object id);
}
