package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppTagDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppTagDaoImpl extends FastJdbcDaoSupport implements AppTagDao {

    @Override
    public List<Map<String, Object>> findTagsByNames(String[] names) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("names", names);
        return getNamedParameterJdbcTemplate().queryForList(
                "SELECT * FROM app_tag WHERE name IN (:names)", parameters);
    }
}
