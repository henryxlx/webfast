package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderBy;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserStatusDao {

    Map<String, Object> addStatus(Map<String, Object> status);

    int deleteStatusesByUserIdAndTypeAndObject(Object userId, Object type, Object objectType, Object objectId);

    List<Map<String, Object>> findStatusesByUserIds(Set<Object> userIds, Integer start, Integer limit);

    List<Map<String, Object>> searchStatuses(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit);
}
