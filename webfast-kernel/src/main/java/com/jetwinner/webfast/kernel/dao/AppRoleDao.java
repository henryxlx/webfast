package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelRole;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppRoleDao {

    List<AppModelRole> listAll();

    int searchRolesCount(Map<String, Object> conditions);

    List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderByBuilder builder, Integer start, Integer limit);

    void insert(Map<String, Object> mapRole);

    int countByRoleName(String roleName);

    int deleteById(Integer id);

    AppModelRole getById(Integer id);

    Map<String, Object> getRoleMapById(Integer id);

    int updateMap(Map<String, Object> mapForRole);
}
