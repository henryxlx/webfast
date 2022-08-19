package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppLogDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppLogDaoImpl extends FastJdbcDaoSupport implements AppLogDao {

    @Override
    public void insertMap(Map<String, Object> logModel) {
        insertMap("app_log", logModel);
    }

    @Override
    public int searchLogCount(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = createLogQueryBuilder(conditions)
                .select("count(`id`) AS count")
                .from("app_log");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<Map<String, Object>> searchLogs(Map<String, Object> conditions, OrderBy orderBy,
                                                Integer start, Integer limit) {

        DynamicQueryBuilder builder = createLogQueryBuilder(conditions)
                .select("*")
                .from("app_log")
                .orderBy(orderBy)
                .setFirstResult(start)
                .setMaxResults(limit);

        return getNamedParameterJdbcTemplate().queryForList(builder.getSQL(), conditions);
    }

    @Override
    public List<Map<String, Object>> analysisLoginDataByTime(long startTime, long endTime) {
        String sql="SELECT count(distinct userid) as count, from_unixtime(createdTime,'%Y-%m-%d') as date FROM `app_log` WHERE `action`='login_success' and `createdTime`>= ? and `createdTime`<= ? group by from_unixtime(`createdTime`,'%Y-%m-%d') order by date ASC ";
        return getJdbcTemplate().queryForList(sql, startTime, endTime);
    }

    private DynamicQueryBuilder createLogQueryBuilder(Map<String, Object> conditions) {
        return new DynamicQueryBuilder(conditions)
			.andWhere("module = :module")
			.andWhere("action = :action")
			.andWhere("level = :level")
			.andWhere("userId = :userId")
			.andWhere("createdTime > :startDateTime")
			.andWhere("createdTime < :endDateTime")
			.andWhere("userId IN ( :userIds )");
    }
}
