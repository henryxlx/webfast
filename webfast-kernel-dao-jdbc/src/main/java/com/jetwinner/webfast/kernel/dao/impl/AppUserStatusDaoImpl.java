package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserStatusDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Repository
public class AppUserStatusDaoImpl extends FastJdbcDaoSupport implements AppUserStatusDao {

    static final String TABLE_NAME = "app_user_status";

    public Map<String, Object> getStatus(Object id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ? LIMIT 1";
        Map<String, Object> status = getJdbcTemplate().queryForList(sql, id).stream().findFirst().orElse(null);
        return status == null ? null : UserStatusSerializer.serialize(status);
    }

    @Override
    public Map<String, Object> addStatus(Map<String, Object> status) {
        UserStatusSerializer.serialize(status);
        Integer id = insertMapReturnKey(TABLE_NAME, status).intValue();
        return getStatus(id);
    }

    @Override
    public int deleteStatusesByUserIdAndTypeAndObject(Object userId, Object type, Object objectType, Object objectId) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE userId = ? AND type = ? AND objectType = ? AND objectId = ?";
        return getJdbcTemplate().update(sql, userId, type, objectType, objectId);
    }

    @Override
    public List<Map<String, Object>> findStatusesByUserIds(Set<Object> userIds, Integer start, Integer limit) {
        if (userIds == null || userIds.size() < 1) {
            return new ArrayList<>(0);
        }
        String marks = repeatQuestionMark(userIds.size());
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE userId IN (" + marks + ");";
        return getJdbcTemplate().queryForList(sql, userIds.toArray());
    }

    @Override
    public List<Map<String, Object>> searchStatuses(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit) {
        DynamicQueryBuilder builder = this.createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderBy)
                .setFirstResult(start)
                .setMaxResults(limit);

        List<Map<String, Object>> statuses = getNamedParameterJdbcTemplate().queryForList(builder.getSQL(), conditions);
        statuses.forEach(e -> UserStatusSerializer.unserialize(e));
        return statuses;
    }

    private DynamicQueryBuilder createSearchQueryBuilder(Map<String, Object> conditions) {
        return new DynamicQueryBuilder(conditions).from(TABLE_NAME).andWhere("private = :private");
    }
}

class UserStatusSerializer {
    private static final String FIELD_NAME = "properties";

    static Map<String, Object> unserialize(Map<String, Object> status) {
        status.put(FIELD_NAME, JsonUtil.jsonDecode(status.get(FIELD_NAME), true));
        return status;
    }

    static Map<String, Object> serialize(Map<String, Object> status) {
        status.put(FIELD_NAME, JsonUtil.objectToString(status.get(FIELD_NAME)));
        return status;
    }
}
