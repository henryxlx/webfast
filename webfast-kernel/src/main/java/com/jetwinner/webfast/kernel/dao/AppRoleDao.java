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

    List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderByBuilder builder, Integer start, Integer limit);
}
