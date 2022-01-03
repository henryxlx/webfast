package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppRoleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppRoleDaoImpl extends FastJdbcDaoSupport implements AppRoleDao {

    private static final String TABLE_NAME = "app_role";

    @Override
    public List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderByBuilder)
                .setFirstResult(start)
                .setMaxResults(limit);
        return getNamedParameterJdbcTemplate().query(builder.getSQL(), conditions,
                new BeanPropertyRowMapper<>(AppModelRole.class));
    }

    private DynamicQueryBuilder createSearchQueryBuilder(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = new DynamicQueryBuilder(conditions)
                .from(TABLE_NAME)
                .andWhere("name = :name")
                .andWhere("code = :code")
                .andWhere("code NOT IN (:excludeCodes)")
                .andWhere("code LIKE :codeLike")
                .andWhere("name LIKE :nameLike")
                .andWhere("createdUserId = :createdUserId");
        return builder;
    }
}
