package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserTokenDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppUserTokenDaoImpl extends FastJdbcDaoSupport implements AppUserTokenDao {

    private static final String TABLE_NAME = "app_user_token";

    @Override
    public void addToken(Map<String, Object> entityMap) {
        insertMap(TABLE_NAME, entityMap);
    }

    @Override
    public Map<String, Object> findTokenByToken(String token) {
        String sql = "SELECT * FROM app_user_token WHERE token = ? LIMIT 1";
        return getJdbcTemplate().queryForList(sql, token).stream().findFirst().orElse(null);
    }

    @Override
    public int deleteToken(Object id) {
        String sql = "DELETE FROM app_user_token WHERE id = ?";
        return getJdbcTemplate().update(sql, id);
    }
}
