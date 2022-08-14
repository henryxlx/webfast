package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.dao.support.DynamicQueryBuilder;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserFieldDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppUserFieldDaoImpl extends FastJdbcDaoSupport implements AppUserFieldDao {

    @Override
    public List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled() {
        String sql = "SELECT * FROM app_user_field where enabled=1 ORDER BY seq";
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public int searchFieldCount(Map<String, Object> condition) {
        DynamicQueryBuilder builder = createSearchQueryBuilder(condition).select("count(id)");
        return getNamedParameterJdbcTemplate().queryForObject(builder.getSQL(), condition, Integer.class);
    }

    @Override
    public List<Map<String, Object>> getAllFieldsOrderBySeq() {
        String sql = "SELECT * FROM app_user_field ORDER BY seq";
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public int addField(Map<String, Object> field) {
        field = ArrayToolkit.part(field, "fieldName", "title", "seq", "enabled", "createdTime");
        return insertMap("app_user_field", field);
    }

    @Override
    public Map<String, Object> getFieldByFieldName(String fieldName) {
        String sql = "SELECT * FROM app_user_field WHERE fieldName = ? LIMIT 1";
        return getJdbcTemplate().queryForList(sql, fieldName).stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Object> getField(Object id) {
        String sql = "SELECT * FROM app_user_field WHERE id = ? LIMIT 1";
        return getJdbcTemplate().queryForList(sql, id).stream().findFirst().orElse(null);
    }

    @Override
    public void deleteField(Object id) {
        getJdbcTemplate().update("DELETE FROM app_user_field WHERE id = ?", id);
    }

    @Override
    public void updateField(Integer id, Map<String, Object> field) {
        updateMap("app_user_field", field, "id");
    }

    private DynamicQueryBuilder createSearchQueryBuilder(Map<String, Object> condition) {
        if (EasyStringUtil.isNotBlank(condition.get("fieldName"))) {
            condition.put("fieldName", "%" + condition.get("fieldName") + "%");
        }

        DynamicQueryBuilder builder = new DynamicQueryBuilder(condition)
                .from("app_user_field")
                .andWhere("enabled = :enabled")
                .andWhere("fieldName like :fieldName");

        return builder;
    }
}
