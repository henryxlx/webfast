package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppBlockHistoryDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
}
