package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppCategoryDao;
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

    private static final String TABLE_NAME = "app_category";

    @Override
    public List<Map<String, Object>> findByIds(Set<Object> ids) {
        if (ids == null || ids.isEmpty()) {
            return ListUtil.newArrayList();
        }
        String marks = ids.stream().map(v -> "?").collect(Collectors.joining(","));
        String sql = String.format("SELECT * FROM %s WHERE id IN (%s)", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql, ids.toArray());
    }
}
