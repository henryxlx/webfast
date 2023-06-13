package com.jetwinner.webfast.kernel.service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppNotificationService {

    void notify(Integer userId, String type, String... content);

    int getUserNotificationCount(Integer userId);

    List<Map<String, Object>> findUserNotifications(Integer userId, Integer start, Integer limit);

    void clearUserNewNotificationCounter(Integer userId);

    void deleteNotificationByUserId(Integer userId);
}
