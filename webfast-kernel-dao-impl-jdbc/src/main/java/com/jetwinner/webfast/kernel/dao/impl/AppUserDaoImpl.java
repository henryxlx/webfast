package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppUserDaoImpl extends FastJdbcDaoSupport implements AppUserDao {

    private static final String TABLE_NAME = "app_user";

    @Override
    public AppUser getByUsername(String username) {
        return getJdbcTemplate().query("SELECT * FROM app_user WHERE username = ?",
                new BeanPropertyRowMapper<>(AppUser.class), username).stream().findFirst().orElse(null);
    }

    @Override
    public void insert(Map<String, Object> user) {
        insertMap(TABLE_NAME, user);
    }

    @Override
    public List<Map<String, Object>> findByIds(Set<Object> ids) {
        if (ids == null || ids.size() == 0) {
            return ListUtil.newArrayList();
        }
        String marks = ids.stream().map(v -> "?").collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM %s WHERE id IN (%s)", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql, ids.toArray());
    }

    @Override
    public AppUser getUser(Object id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(AppUser.class), id)
                .stream().findFirst().orElse(null);
    }
}
