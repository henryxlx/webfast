package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentDao {

    void insert(Map<String, Object> model);

    List<Map<String, Object>> findAll();
}
