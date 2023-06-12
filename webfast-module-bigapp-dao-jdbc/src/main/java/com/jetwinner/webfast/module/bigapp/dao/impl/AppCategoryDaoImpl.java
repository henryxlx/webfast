package com.jetwinner.webfast.module.bigapp.dao.impl;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.bigapp.dao.AppCategoryDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppCategoryDaoImpl extends FastJdbcDaoSupport implements AppCategoryDao {

    private static final String TABLE_NAME = "big_app_category";

    @Override
    public List<Map<String, Object>> findByIds(Set<Object> ids) {
        if (ids == null || ids.isEmpty()) {
            return ListUtil.newArrayList();
        }
        String marks = ids.stream().map(v -> "?").collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM %s WHERE id IN (%s)", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql, ids.toArray());
    }

    @Override
    public List<Map<String, Object>> findCategoriesByGroupId(Object groupId) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_category WHERE groupId = ? ORDER BY weight ASC", groupId);
    }

    @Override
    public Map<String, Object> getCategory(Object id) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_category WHERE id = ? LIMIT 1", id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Object> addCategory(Map<String, Object> fields) {
        int id = insertMapReturnKey(TABLE_NAME, fields).intValue();
        return getCategory(id);
    }

    @Override
    public Map<String, Object> findCategoryByCode(String code) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_category WHERE code = ? LIMIT 1", code)
                .stream().findFirst().orElse(null);
    }

    @Override
    public void updateCategory(Integer id, Map<String, Object> fields) {
        fields.put("id", id);
        updateMap(TABLE_NAME, fields, "id");
    }

    @Override
    public void deleteByIds(Set<Object> ids) {
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", ids);
        getNamedParameterJdbcTemplate().update("DELETE FROM big_app_category WHERE id in (:ids)", parameters);
    }

    @Override
    public List<Map<String, Object>> findAllCategoriesByParentId(Integer parentId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE parentId = ? ORDER BY weight ASC";
        return getJdbcTemplate().queryForList(sql, parentId);
    }
}
