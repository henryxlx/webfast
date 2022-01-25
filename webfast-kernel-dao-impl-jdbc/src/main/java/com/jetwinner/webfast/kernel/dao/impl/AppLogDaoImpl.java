package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppLogDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AppLogDaoImpl extends FastJdbcDaoSupport implements AppLogDao {

    @Override
    public void insertMap(Map<String, Object> logModel) {
        insertMap("app_log", logModel);
    }
}
