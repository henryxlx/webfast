package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNotificationDao {

    void addNotification(Map<String, Object> notification);

    int getNotificationCountByUserId(Integer userId);

    List<Map<String, Object>> findNotificationsByUserId(Integer userId, Integer start, Integer limit);
}
