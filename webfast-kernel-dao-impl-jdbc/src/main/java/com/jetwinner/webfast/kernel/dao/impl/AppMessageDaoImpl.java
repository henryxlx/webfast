package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppMessageDao;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppMessageDaoImpl extends FastJdbcDaoSupport implements AppMessageDao {

    @Override
    public AppModelMessage addMessage(Map<String, Object> entityMap) {
        return null;
    }
}
