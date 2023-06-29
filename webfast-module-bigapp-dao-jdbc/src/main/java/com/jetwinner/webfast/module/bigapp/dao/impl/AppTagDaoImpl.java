package com.jetwinner.webfast.module.bigapp.dao.impl;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.module.bigapp.dao.AppTagDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppTagDaoImpl extends FastJdbcDaoSupport implements AppTagDao {

    @Override
    public List<Map<String, Object>> findTagsByNames(String[] names) {
        if (names == null || names.length < 1) {
            return null;
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("names", Arrays.asList(names));
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

    @Override
    public List<Map<String, Object>> findTagsByIds(String[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ListUtil.newArrayList(ids));
        return getNamedParameterJdbcTemplate().queryForList("SELECT * FROM big_app_tag WHERE id in (:ids)",
                        parameters);
    }

    @Override
    public int findAllTagsCount() {
        return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM big_app_tag ", Integer.class);
    }

    @Override
    public List<Map<String, Object>> findAllTags(Integer start, Integer limit) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_tag ORDER BY createdTime DESC LIMIT ?, ?",
                start, limit);
    }

    @Override
    public Map<String, Object> getTag(Integer id) {
        return getJdbcTemplate().queryForList("SELECT * FROM big_app_tag WHERE id = ? LIMIT 1", id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public int updateTag(Integer id, Map<String, Object> fields) {
        fields.put("id", id);
        return updateMap("big_app_tag", fields, "id");
    }

    @Override
    public int deleteTag(Integer id) {
        return getJdbcTemplate().update("DELETE FROM big_app_tag WHERE id = ?", id);
    }
}
