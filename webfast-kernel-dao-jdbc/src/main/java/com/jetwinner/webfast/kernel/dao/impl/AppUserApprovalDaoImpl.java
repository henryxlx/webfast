package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserApprovalDao;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppUserApprovalDaoImpl extends FastJdbcDaoSupport implements AppUserApprovalDao {

    private static final String TABLE_NAME = "app_user_approval";

    @Override
    public void addApproval(Map<String, Object> entityMap) {
        insertMap(TABLE_NAME, entityMap);
    }

    @Override
    public List<Map<String, Object>> findApprovalsByUserIds(Set<Object> userIds) {
        if(userIds == null || userIds.size() < 1){
            return new ArrayList<>(0);
        }

        String marks = userIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM %s WHERE userId IN (%s);", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public Map<String, Object> getLastestApprovalByUserIdAndStatus(Integer userId, String status) {
        String sql = "SELECT * FROM app_user_approval WHERE userId = ? AND status = ? ORDER BY createdTime DESC LIMIT 1";
        return getJdbcTemplate().queryForList(sql, userId, status).stream().findFirst().orElse(new HashMap<>(0));
    }
}
