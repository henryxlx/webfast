package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppNavigationDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppNavigationDaoImpl extends FastJdbcDaoSupport implements AppNavigationDao {

    @Override
    public void insert(Map<String, Object> model) {
        insertMap("app_navigation", model);
    }

    @Override
    public Integer getNavigationsCountByType(String type) {
        String sql = "SELECT COUNT(id) FROM app_navigation WHERE type = ?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, type);
    }
}
