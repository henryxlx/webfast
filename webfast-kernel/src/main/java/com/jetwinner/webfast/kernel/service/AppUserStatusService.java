package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserStatusService {

    default Map<String, Object> publishStatus(Map<String, Object> status, AppUser currentUser) {
        return this.publishStatus(status, true, currentUser);
    }

    Map<String, Object> publishStatus(Map<String, Object> status, Boolean deleteOld, AppUser currentUser);

    List<Map<String, Object>> findStatusesByUserIds(Set<Object> userIds, Integer start, Integer limit);

    List<Map<String, Object>> searchStatuses(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit);
}
