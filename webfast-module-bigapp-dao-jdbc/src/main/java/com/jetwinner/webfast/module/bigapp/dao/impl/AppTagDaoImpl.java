package com.jetwinner.webfast.module.bigapp.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.bigapp.dao.AppTagDao;
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
                "SELECT * FROM big_app_tag WHERE name IN (:names)", parameters);
    }

    @Override
    public Map<String, Object> addTag(Map<String, Object> tagMap) {
        Number idNumber = insertMapReturnKey("big_app_tag", tagMap);
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_tag WHERE id = ?", idNumber.intValue())
                .stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Object> getTagByName(String name) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_tag WHERE name = ? LIMIT 1", name)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Map<String, Object>> getTagByLikeName(String partOfName) {
        String name = "%" + partOfName + "%";
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_tag WHERE name LIKE ?", name);
    }
}
