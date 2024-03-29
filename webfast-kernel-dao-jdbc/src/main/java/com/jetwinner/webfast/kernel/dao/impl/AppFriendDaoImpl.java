package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppFriendDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppFriendDaoImpl extends FastJdbcDaoSupport implements AppFriendDao {

    private static final String TABLE_NAME = "app_friend";

    @Override
    public List<Map<String, Object>> getFriendsByFromIdAndToIds(Integer fromId, Set<Object> toIds) {
        if (toIds == null || toIds.size() == 0) {
            return new ArrayList<>(0);
        }
        String marks = toIds.stream().map(String::valueOf).collect(Collectors.joining(", "));
        String sql = String.format("SELECT * FROM %s WHERE fromId = ? AND toId IN (%s);", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql, fromId);
    }

    @Override
    public Map<String, Object> getFriendByFromIdAndToId(Integer fromId, Integer toId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE fromId = ? AND toId = ?";
        return getJdbcTemplate().queryForList(sql, fromId, toId).stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Object> addFriend(Map<String, Object> fields) {
        int id = insertMapReturnKey(TABLE_NAME, fields).intValue();
        if (id > 0) {
            fields.put("id", id);
            return fields;
        }
        return MapUtil.newHashMap(0);
    }

    @Override
    public int deleteFriend(Object id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        return getJdbcTemplate().update(sql, id);
    }

    @Override
    public List<Map<String, Object>> findAllUserFollowingByFromId(Integer fromId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE fromId = ? ORDER BY createdTime DESC ";
        return getJdbcTemplate().queryForList(sql, fromId);
    }

    @Override
    public List<Map<String, Object>> findAllUserFollowerByToId(Integer toId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE toId = ? ORDER BY createdTime DESC ";
        return getJdbcTemplate().queryForList(sql, toId);
    }
}
