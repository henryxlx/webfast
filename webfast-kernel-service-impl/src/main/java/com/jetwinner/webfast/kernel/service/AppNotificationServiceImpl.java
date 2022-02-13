package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.dao.AppNotificationDao;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppNotificationServiceImpl implements AppNotificationService {

    private final AppNotificationDao notificationDao;
    private final AppUserDao userDao;

    public AppNotificationServiceImpl(AppNotificationDao notificationDao, AppUserDao userDao) {
        this.notificationDao = notificationDao;
        this.userDao = userDao;
    }

    @Override
    public void notify(Integer userId, String type, String... content) {
        Map<String, Object> notification = new HashMap<>(5);
        notification.put("userId", userId);
        notification.put("type", EasyStringUtil.isBlank(type) ? "default" : type);
        notification.put("content", JsonUtil.objectToString(content));
        notification.put("createdTime", System.currentTimeMillis());
        notification.put("isRead", 0);
        notificationDao.addNotification(notification);
        userDao.waveCounterById(userId, "newNotificationNum", 1);
    }

    @Override
    public int getUserNotificationCount(Integer userId) {
        return notificationDao.getNotificationCountByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> findUserNotifications(Integer userId, Integer start, Integer limit) {
        return notificationDao.findNotificationsByUserId(userId, start, limit);
    }

    @Override
    public void clearUserNewNotificationCounter(Integer userId) {
        userDao.clearCounterById(userId, "newNotificationNum");
    }
}
