package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppRole;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppRoleService {

    List<AppRole> listAllRole();

    int searchRolesCount(Map<String, Object> conditions);

    List<AppRole> searchRoles(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit);

    void addRole(Map<String, Object> mapRole);

    boolean isRoleNameAvailable(String roleName, String exclude);

    int deleteRoleById(Integer id);

    AppRole getRoleById(Integer id);

    Map<String, Object> getRoleMapById(Integer id);

    boolean updateRoleMap(Map<String, Object> mapForUpdate);

    void updateRolePermissions(Integer id, String[] permissionKeys);
}
