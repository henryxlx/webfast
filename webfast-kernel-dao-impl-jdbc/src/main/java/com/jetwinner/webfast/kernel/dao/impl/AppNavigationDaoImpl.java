package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppNavigationDao;
import com.jetwinner.webfast.kernel.model.AppModelNavigation;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public int countNavigationsByType(String type) {
        return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM app_navigation WHERE  type = ?", Integer.class, type);
    }

    @Override
    public List<AppModelNavigation> findAllByType(String type, int start, int limit) {
        return getJdbcTemplate().query("SELECT * FROM app_navigation WHERE type = ? ORDER BY sequence ASC LIMIT ?, ?",
                new BeanPropertyRowMapper<>(AppModelNavigation.class), type, start, limit);
    }
}
