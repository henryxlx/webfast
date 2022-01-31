package com.jetwinner.webfast.kernel.dao.impl;

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
}
