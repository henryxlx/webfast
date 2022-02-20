package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.AppRole;
import com.jetwinner.webfast.kernel.dao.AppRoleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
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
    public List<AppRole> listAll() {
        return getJdbcTemplate().query("SELECT * FROM " + TABLE_NAME, new BeanPropertyRowMapper<>(AppRole.class));
    }

    @Override
    public int searchRolesCount(Map<String, Object> conditions) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("COUNT(id)");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), conditions, Integer.class);
    }

    @Override
    public List<AppRole> searchRoles(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(conditions)
                .select("*")
                .orderBy(orderByBuilder)
                .setFirstResult(start)
                .setMaxResults(limit);
        return getNamedParameterJdbcTemplate().query(builder.getSQL(), conditions,
                new BeanPropertyRowMapper<>(AppRole.class));
    }

    @Override
    public void insert(Map<String, Object> mapRole) {
        Long now = System.currentTimeMillis();
        mapRole.put("createdTime", now);
        mapRole.put("updatedTime", now);
        Integer id = insertMapReturnKey(TABLE_NAME, mapRole).intValue();
        mapRole.put("id", id);
    }

    @Override
    public int countByRoleName(String roleName) {
        return getJdbcTemplate().queryForObject("SELECT count(roleName) FROM app_role WHERE roleName = ?",
                Integer.class, roleName);
    }

    @Override
    public int deleteById(Integer id) {
        return getJdbcTemplate().update("DELETE FROM app_role WHERE id = ?", id);
    }

    @Override
    public AppRole getById(Integer id) {
        return getJdbcTemplate().query("SELECT * FROM app_role WHERE id = ?",
                        new BeanPropertyRowMapper<>(AppRole.class), id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Object> getRoleMapById(Integer id) {
        return getJdbcTemplate().queryForList("SELECT * FROM app_role WHERE id = ?", id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public int updateMap(Map<String, Object> mapRole) {
        mapRole.put("updatedTime", System.currentTimeMillis());
        return updateMap(TABLE_NAME, mapRole, "id");
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
