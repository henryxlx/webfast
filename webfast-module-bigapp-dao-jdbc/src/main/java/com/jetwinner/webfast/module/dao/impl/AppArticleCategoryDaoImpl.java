package com.jetwinner.webfast.module.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.dao.AppArticleCategoryDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppArticleCategoryDaoImpl extends FastJdbcDaoSupport implements AppArticleCategoryDao {

    private static final String TABLE_NAME = "big_app_article_category";

    @Override
    public Map<String, Object> getCategory(Object id) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_article_category WHERE id = ? LIMIT 1", id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Map<String, Object>> findCategoriesByIds(Set<Object> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>(0);
        }
        String marks = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM big_app_article_category WHERE id IN (%s);", marks);
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> findAllCategories() {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_article_category ORDER BY weight ASC");
    }

    @Override
    public Map<String, Object> addCategory(Map<String, Object> category) {
        Number key = insertMapReturnKey(TABLE_NAME, category);
        return getCategory(key.intValue());
    }

    @Override
    public Map<String, Object> findCategoryByCode(String code) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_article_category WHERE code = ? LIMIT 1", code)
                .stream().findFirst().orElse(null);
    }

    @Override
    public void updateCategory(Integer id, Map<String, Object> fields) {
        fields.put("id", id);
        updateMap(TABLE_NAME, fields, "id");
    }

    @Override
    public int findCategoriesCountByParentId(Integer parentId) {
        return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM big_app_article_category WHERE  parentId = ?",
                Integer.class, parentId);
    }

    @Override
    public void deleteByIds(Set<Object> ids) {
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ids", ids);
        getNamedParameterJdbcTemplate().update("DELETE FROM big_app_article_category WHERE id in (:ids)", parameters);
    }
}
