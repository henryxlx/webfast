package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppBlockDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppBlockDaoImpl extends FastJdbcDaoSupport implements AppBlockDao {

    private static final String TABlE_NAME = "app_block";

    @Override
    public Integer insert(Map<String, Object> model) {
        return insertMapReturnKey(TABlE_NAME, model).intValue();
    }

    @Override
    public int searchBlockCount() {
        String sql = "SELECT COUNT(*) FROM " + TABlE_NAME;
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public List<Map<String, Object>> findBlocks(int start, int limit) {
        String sql = String.format("SELECT * FROM %s ORDER BY createdTime DESC LIMIT %d, %d", TABlE_NAME, start, limit);
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public Map<String, Object> getBlock(Integer id) {
        String sql = "SELECT * FROM app_block WHERE id = ? LIMIT 1";
        return getJdbcTemplate().queryForList(sql, id).stream().findFirst().orElse(null);
    }

    @Override
    public void updateBlock(Integer id, Map<String, Object> fields) {
        updateMap(TABlE_NAME, fields, "id", id);
    }
}
