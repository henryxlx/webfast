package com.jetwinner.webfast.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author xulixin
 */
@Repository
public class AppUserDaoImpl extends FastJdbcDaoSupport implements AppUserDao {

    private static final String TABLE_NAME = "app_user";

    @Override
    public AppUser getByUsername(String username) {
        return getJdbcTemplate().queryForObject("SELECT * FROM app_user WHERE username = ?",
                new BeanPropertyRowMapper<>(AppUser.class), username);
    }

    @Override
    public void insert(AppUser user) {
        insert(TABLE_NAME, user);
    }
}
