package com.jetwinner.webfast.kernel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNotificationService {

    default void notify(Integer userId, String type, String content) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("message", content);
        this.notify(userId, type, map);
    }

    void notify(Integer userId, String type, Map<String, Object> content);

    int getUserNotificationCount(Integer userId);

    List<Map<String, Object>> findUserNotifications(Integer userId, Integer start, Integer limit);

    void clearUserNewNotificationCounter(Integer userId);

    void deleteNotificationByUserId(Integer userId);
}
