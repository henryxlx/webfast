package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.dao.AppNotificationDao;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

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
        Map<String, Object> notification = ParamMap.toNewHashMap(5);
        notification.put("userId", userId);
        notification.put("type", EasyStringUtil.isBlank(type) ? "default" : type);
        notification.put("content", JsonUtil.objectToString(content));
        notification.put("createdTime", System.currentTimeMillis());
        notification.put("isRead", 0);
        notificationDao.addNotification(notification);
        userDao.waveCounterById(userId, "newNotificationNum", 1);
    }
}
