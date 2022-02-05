package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppArticleCategoryDao;
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

    @Override
    public Map<String, Object> getCategory(Object id) {
        return getJdbcTemplate().queryForList("SELECT * FROM app_article_category WHERE id = ? LIMIT 1", id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Map<String, Object>> findCategoriesByIds(Set<Object> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>(0);
        }
        String marks = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM app_article_category WHERE id IN (%s);", marks);
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> findAllCategories() {
        return getJdbcTemplate().queryForList("SELECT * FROM app_article_category ORDER BY weight ASC");
    }
}
