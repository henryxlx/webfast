package com.jetwinner.webfast.module.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.dao.AppCategoryGroupDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppCategoryGroupDaoImpl extends FastJdbcDaoSupport implements AppCategoryGroupDao {

    @Override
    public Map<String, Object> findGroupByCode(String code) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_category_group WHERE code = ? LIMIT 1", code)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Object> getGroup(Object id) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_category_group WHERE id = ? LIMIT 1", id)
                .stream().findFirst().orElse(toEmptyMap());
    }

}
