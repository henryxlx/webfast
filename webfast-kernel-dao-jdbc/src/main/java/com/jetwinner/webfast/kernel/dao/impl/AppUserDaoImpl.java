package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
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
        user.put("createdTime", System.currentTimeMillis());
        insertMap(TABLE_NAME, user);
    }

    @Override
    public List<AppUser> findByIds(Set<Object> ids) {
        if (ids == null || ids.size() == 0) {
            return ListUtil.newArrayList();
        }
        String marks = ids.stream().map(v -> "?").collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM %s WHERE id IN (%s)", TABLE_NAME, marks);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(AppUser.class), ids.toArray());
    }

    @Override
    public AppUser getUser(Object id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(AppUser.class), id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public int searchUserCount(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = createUserQueryBuilder(conditions).select("COUNT(id)");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<AppUser> searchUsers(Map<String, Object> conditions, OrderBy orderBy,
                                     Integer start, Integer limit) {

        DynamicQueryBuilder builder = createUserQueryBuilder(conditions)
                .select("*")
                .orderBy(orderBy)
                .setFirstResult(start)
                .setMaxResults(limit);
        return getNamedParameterJdbcTemplate().query(builder.getSQL(), conditions, new BeanPropertyRowMapper<>(AppUser.class));
    }

    @Override
    public Map<String, Object> getProfile(Integer id) {
        String sql = "SELECT * FROM app_user_profile WHERE id = ? LIMIT 1";
        return getJdbcTemplate().queryForList(sql, id).stream().findFirst().orElse(new HashMap<>(0));
    }

    @Override
    public int countForEmail(String email) {
        return getJdbcTemplate().queryForObject("SELECT COUNT(id) FROM app_user WHERE email = ?", Integer.class, email);
    }

    @Override
    public int updateMap(Map<String, Object> mapUser) {
        return updateMap(TABLE_NAME, mapUser, "id");
    }

    @Override
    public int waveCounterById(Integer userId, String fieldName, int number) {
        String sql = String.format("UPDATE %s SET %s = %s + ? WHERE id = ? LIMIT 1",
                TABLE_NAME, fieldName, fieldName);
        if (!EasyStringUtil.inArray(fieldName, "newMessageNum", "newNotificationNum")) {
            throw new BadSqlGrammarException("UPDATE user message or notification number: ", sql,
                    new SQLException("counter name error"));
        }
        return getJdbcTemplate().update(sql, number, userId);
    }

    @Override
    public int clearCounterById(Integer userId, String fieldName) {
        String sql = String.format("UPDATE %s SET %s = 0 WHERE id = ? LIMIT 1", TABLE_NAME, fieldName);
        if (!EasyStringUtil.inArray(fieldName, "newMessageNum", "newNotificationNum")) {
            throw new BadSqlGrammarException("UPDATE user message or notification number: ", sql,
                    new SQLException("counter name error"));
        }
        return getJdbcTemplate().update(sql, userId);
    }

    @Override
    public AppUser getByEmail(String email) {
        String sql = String.format("SELECT * FROM %s WHERE email = ? LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(AppUser.class), email)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Map<String, Object>> analysisRegisterDataByTime(long startTime, long endTime) {
        String sql = "SELECT count(id) as count, from_unixtime(createdTime/1000,'%Y-%m-%d') as date FROM app_user WHERE`createdTime`>=? and `createdTime`<=? group by from_unixtime(`createdTime`/1000,'%Y-%m-%d') order by date ASC ";
        return getJdbcTemplate().queryForList(sql, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> analysisUserSumByTime(long endTime) {
        String sql="select date, count(*) as count from (SELECT from_unixtime(o.createdTime/1000,'%Y-%m-%d') as date from app_user o where o.createdTime<=? ) dates group by dates.date order by date desc";
        return getJdbcTemplate().queryForList(sql, endTime);
    }

    private DynamicQueryBuilder createUserQueryBuilder(Map<String, Object> conditions) {

        if (conditions.containsKey("roles")) {
            conditions.put("rolesLike", "%" + conditions.get("roles") + "%");
        }

        if (conditions.containsKey("role")) {
            conditions.put("roleEqual", "|" + conditions.get("role"));
        }

        if (conditions.containsKey("keywordType") && conditions.containsKey("keyword")) {
            conditions.put(String.valueOf(conditions.get("keywordType")), conditions.get("keyword"));
            conditions.remove("keywordType");
            conditions.remove("keyword");
        }

        if (conditions.containsKey("username")) {
            conditions.put("usernameLike", "%" + conditions.get("username") + "%");
        }

        return new DynamicQueryBuilder(conditions)
                .from(TABLE_NAME, "user")
                .andWhere("promoted = :promoted")
                .andWhere("roles LIKE :rolesLike")
                .andWhere("roles = :roleEqual")
                .andWhere("username LIKE :usernameLike")
                .andWhere("loginIp = :loginIp")
                .andWhere("createdIp = :createdIp")
                .andWhere("approvalStatus = :approvalStatus")
                .andWhere("email = :email")
                .andWhere("level = :level")
                .andWhere("createdTime >= :startTime")
                .andWhere("createdTime <= :endTime")
                .andWhere("locked = :locked")
                .andWhere("level >= :greatLevel")
                .andWhere("verifiedMobile = :verifiedMobile");
    }
}
