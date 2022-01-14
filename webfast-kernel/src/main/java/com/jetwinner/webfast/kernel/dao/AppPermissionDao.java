package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppPermissionDao {

    List<Map<String, Object>> findAll();

    void insert(Map<String, Object> mapPermission);

    boolean isPermissionKeyAvailable(String permissionKey, String exclude);

    int delete(Integer id);

    Map<String, Object> get(Integer id);

    void update(Map<String, Object> mapPermission);
}
