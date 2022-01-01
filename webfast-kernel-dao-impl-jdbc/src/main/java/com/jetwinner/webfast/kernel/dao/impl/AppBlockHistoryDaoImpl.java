package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppBlockHistoryDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppBlockHistoryDaoImpl extends FastJdbcDaoSupport implements AppBlockHistoryDao {

    private static final String TABLE_NAME = "app_block_history";

    @Override
    public Map<String, Object> getLatestBlockHistory() {
        String sql = String.format("SELECT * FROM %s ORDER BY createdTime DESC LIMIT 1", TABLE_NAME);
        return getJdbcTemplate().queryForList(sql).stream().findFirst().orElse(new HashMap<>(0));
    }

    @Override
    public int countByBlockId(Object blockId) {
        return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM app_block_history WHERE blockId = ?",
                Integer.class, blockId);
    }

    @Override
    public List<Map<String, Object>> findByBlockId(Object blockId, Integer start, Integer limit) {
        String sql = String.format("SELECT * FROM %s WHERE blockId = ? ORDER BY createdTime DESC LIMIT %d, %d",
                TABLE_NAME, start, limit);
        return getJdbcTemplate().queryForList(sql, blockId);
    }
}
