package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserApprovalDao {

    void addApproval(Map<String, Object> entityMap);

    List<Map<String, Object>> findApprovalsByUserIds(Set<Object> userIds);

    Map<String, Object> getLastestApprovalByUserIdAndStatus(Integer userId, String status);
}
