package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppNotificationDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppNotificationDaoImpl extends FastJdbcDaoSupport implements AppNotificationDao {

    @Override
    public void addNotification(Map<String, Object> notification) {
        insertMap("app_notification", notification);
    }

    @Override
    public int getNotificationCountByUserId(Integer userId) {
        String sql = "SELECT COUNT(*) FROM app_notification WHERE userId = ?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, userId);
    }

    @Override
    public List<Map<String, Object>> findNotificationsByUserId(Integer userId, Integer start, Integer limit) {
        String sql = "SELECT * FROM app_notification WHERE userId = ? ORDER BY createdTime DESC LIMIT ?, ?";
        return getJdbcTemplate().queryForList(sql, userId, start, limit);
    }

    @Override
    public int deleteByUserId(Integer userId) {
        String sql = "DELETE FROM app_notification WHERE userId = ?";
        return getJdbcTemplate().update(sql, userId);
    }
}
