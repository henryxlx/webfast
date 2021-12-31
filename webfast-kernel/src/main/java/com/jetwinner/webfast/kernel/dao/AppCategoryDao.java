package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppCategoryDao {

    List<Map<String, Object>> findByIds(Set<Object> ids);
}
