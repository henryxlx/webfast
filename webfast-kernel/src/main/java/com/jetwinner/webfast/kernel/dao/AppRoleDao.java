package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.AppRole;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppRoleDao {

    List<AppRole> listAll();

    int searchRolesCount(Map<String, Object> conditions);

    List<AppRole> searchRoles(Map<String, Object> conditions, OrderByBuilder builder, Integer start, Integer limit);

    void insert(Map<String, Object> mapRole);

    int countByRoleName(String roleName);

    int deleteById(Integer id);

    AppRole getById(Integer id);

    Map<String, Object> getRoleMapById(Integer id);

    int updateMap(Map<String, Object> mapRole);
}
