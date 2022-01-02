package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.model.AppModelRole;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppRoleService {

    int searchRolesCount(Map<String, Object> conditions);

    List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderBy[] sort, Integer start, Integer limit);
}
