package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppMessageDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppMessageDaoImpl extends FastJdbcDaoSupport implements AppMessageDao {

    private static final String TABLE_NAME = "app_message";

    @Override
    public AppModelMessage getMessage(int id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(AppModelMessage.class), id);
    }

    @Override
    public int searchMessagesCount(Map<String, Object> conditions) {
        if (EasyStringUtil.isNotBlank(conditions.get("content"))) {
            conditions.put("content", "%" + conditions.get("content") + "%");
        }

        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("COUNT(id)");

        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<AppModelMessage> searchMessages(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                                int start, int limit) {

        if (EasyStringUtil.isNotBlank(conditions.get("content"))) {
            conditions.put("content", "%" + conditions.get("content") + "%");
        }

        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .setFirstResult(start)
                .setMaxResults(limit)
                .orderBy(new OrderByBuilder().addDesc("createdTime"));

        return getNamedParameterJdbcTemplate().query(builder.getSQL(), conditions,
                new BeanPropertyRowMapper<>(AppModelMessage.class));
    }

    @Override
    public int addMessage(Map<String, Object> fields) {
        return insertMapReturnKey(TABLE_NAME, fields).intValue();
    }

    @Override
    public List<AppModelMessage> findMessagesByIds(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>(0);
        }
        String marks = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));
        String sql = String.format("SELECT * FROM %s WHERE id IN (%s);", TABLE_NAME, marks);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(AppModelMessage.class), ids.toArray());
    }

    private DynamicQueryBuilder createSearchQueryBuilder(Map<String, Object> conditions) {
        return new DynamicQueryBuilder(conditions)
                .from(TABLE_NAME)
                .andWhere("fromId = :fromId")
                .andWhere("toId = :toId")
                .andWhere("createdTime = :createdTime")
                .andWhere("createdTime >= :startDate")
                .andWhere("createdTime < :endDate")
                .andWhere("content LIKE :content");
    }
}
