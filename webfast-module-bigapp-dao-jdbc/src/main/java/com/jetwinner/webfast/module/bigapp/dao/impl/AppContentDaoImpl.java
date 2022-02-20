package com.jetwinner.webfast.module.bigapp.dao.impl;

import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.bigapp.dao.AppContentDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppContentDaoImpl extends FastJdbcDaoSupport implements AppContentDao {

    private static String TABLE_NAME = "big_app_content";

    @Override
    public void insert(Map<String, Object> model) {
        int id = insertMapReturnKey(TABLE_NAME, model).intValue();
        if (id > 0) {
            model.put("id", id);
        }
    }

    @Override
    public int searchContentCount(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions).select("COUNT(id)");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<Map<String, Object>> searchContent(Map<String, Object> conditions,
                                                   OrderByBuilder orderByBuilder,
                                                   Integer start, Integer limit) {

        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderByBuilder)
                .setFirstResult(start)
                .setMaxResults(limit);
        return getNamedParameterJdbcTemplate().queryForList(builder.getSQL(), conditions);
    }

    @Override
    public Map<String, Object> getContent(Integer id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().queryForMap(sql, id);
    }

    @Override
    public void updateContent(Integer id, Map<String, Object> fields) {
        updateMap(TABLE_NAME, fields, "id", id);
    }

    @Override
    public Map<String, Object> getContentByAlias(String alias) {
        String sql = String.format("SELECT * FROM %s WHERE alias = ? LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().queryForMap(sql, alias);
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
