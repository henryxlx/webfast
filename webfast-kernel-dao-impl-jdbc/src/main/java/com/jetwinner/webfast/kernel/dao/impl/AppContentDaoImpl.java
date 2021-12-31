package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppContentDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppContentDaoImpl extends FastJdbcDaoSupport implements AppContentDao {

    private static String TABLE_NAME = "app_content";

    @Override
    public void insert(Map<String, Object> model) {
        insertMap(TABLE_NAME, model);
    }

    @Override
    public int searchContentCount(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions).select("COUNT(id)");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<Map<String, Object>> searchContent(Map<String, Object> conditions,
                                                   OrderBy[] orderByArray,
                                                   Integer start, Integer limit) {

        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderByArray)
                .setFirstResult(start)
                .setMaxResults(limit);
        return getNamedParameterJdbcTemplate().queryForList(builder.getSQL(), conditions);
    }

    private DynamicQueryBuilder createSearchQueryBuilder(Map<String, Object> conditions) {
        if (conditions.containsKey("keywords")) {
            conditions.put("keywordsLike", "%" + conditions.get("keywords") + "%");
        }

        DynamicQueryBuilder builder = new DynamicQueryBuilder(conditions)
                .from(TABLE_NAME, "content")
                .andWhere("type = :type")
                .andWhere("status = :status")
                .andWhere("title LIKE :keywordsLike")
                .andWhere("categoryId IN (:categoryIds)");
        return builder;
    }
}
