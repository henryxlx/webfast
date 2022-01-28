package com.jetwinner.webfast.kernel.dao.impl;

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
        if (toIds == null || toIds.size() == 0) { return new ArrayList<>(0); }
        String marks = toIds.stream().map(String::valueOf).collect(Collectors.joining(", "));
        String sql = String.format("SELECT * FROM %s WHERE fromId = ? AND toId IN (%s);", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql, fromId);
    }
}
