package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppNotificationDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppNotificationDaoImpl extends FastJdbcDaoSupport implements AppNotificationDao {

    @Override
    public void addNotification(Map<String, Object> notification) {
        insert("app_notification", notification);
    }
}
