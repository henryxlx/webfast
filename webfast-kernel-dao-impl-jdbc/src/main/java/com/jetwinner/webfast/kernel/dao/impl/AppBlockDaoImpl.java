package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppBlockDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppBlockDaoImpl extends FastJdbcDaoSupport implements AppBlockDao {

    @Override
    public Integer insert(Map<String, Object> model) {
        return insertMapReturnKey("app_block", model).intValue();
    }
}
