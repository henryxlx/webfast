package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelRole;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppRoleService {

    List<AppModelRole> listAllRole();

    int searchRolesCount(Map<String, Object> conditions);

    List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit);

    void addRole(Map<String, Object> mapRole);

    boolean isRoleNameAvailable(String roleName, String exclude);

    int deleteRoleById(Integer id);

    AppModelRole getRoleById(Integer id);

    Map<String, Object> getRoleMapById(Integer id);

    boolean updateRoleMap(Map<String, Object> mapForUpdate);
}
