package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelRole;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppRoleService {

    int searchRolesCount(Map<String, Object> conditions);

    List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit);
}