package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppNavigationDao;
import com.jetwinner.webfast.kernel.model.AppModelNavigation;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppNavigationDaoImpl extends FastJdbcDaoSupport implements AppNavigationDao {

    @Override
    public int insert(Map<String, Object> model) {
        return insertMap("app_navigation", model);
    }

    @Override
    public Integer getNavigationsCountByType(String type) {
        String sql = "SELECT COUNT(id) FROM app_navigation WHERE type = ?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, type);
    }

    @Override
    public int countNavigationsByType(String type) {
        return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM app_navigation WHERE  type = ?", Integer.class, type);
    }

    @Override
    public List<AppModelNavigation> findAllByType(String type, int start, int limit) {
        return getJdbcTemplate().query("SELECT * FROM app_navigation WHERE type = ? ORDER BY sequence ASC LIMIT ?, ?",
                new BeanPropertyRowMapper<>(AppModelNavigation.class), type, start, limit);
    }

    @Override
    public int deleteById(Integer id) {
        return getJdbcTemplate().update("DELETE FROM app_navigation WHERE id = ?", id);
    }

    @Override
    public int deleteByParentId(Integer parentId) {
        return getJdbcTemplate().update("DELETE FROM app_navigation WHERE parentId = ?", parentId);
    }

    @Override
    public AppModelNavigation getById(Integer id) {
        return getJdbcTemplate().query("SELECT * FROM app_navigation WHERE id = ? LIMIT 1",
                new BeanPropertyRowMapper<>(AppModelNavigation.class), id).stream().findFirst().orElse(null);
    }

    @Override
    public int updateNavigation(Map<String, Object> fields) {
        return updateMap("app_navigation", fields, "id");
    }

    @Override
    public int updateSequenceByIds(List<Object> ids) {
        String SQL = "UPDATE app_navigation SET sequence = ? WHERE id = ?";
        int[] updateCounts = getJdbcTemplate().batchUpdate(SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, i + 1);
                ps.setInt(2, ValueParser.parseInt(ids.get(i)));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });
        int nums = 0;
        for (int count : updateCounts) {
            nums += count;
        }
        return nums;
    }
}
