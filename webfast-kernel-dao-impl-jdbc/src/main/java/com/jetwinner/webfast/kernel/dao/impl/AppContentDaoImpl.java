package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppContentDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppContentDaoImpl extends FastJdbcDaoSupport implements AppContentDao {

    @Override
    public void insert(Map<String, Object> model) {
        insertMap("app_content", model);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        return getJdbcTemplate().queryForList("SELECT * FROM app_content");
    }
}
