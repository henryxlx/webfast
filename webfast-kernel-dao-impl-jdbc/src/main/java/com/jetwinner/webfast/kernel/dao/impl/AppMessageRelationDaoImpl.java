package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppMessageRelationDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppMessageRelationDaoImpl extends FastJdbcDaoSupport implements AppMessageRelationDao {

    @Override
    public void addRelation(Map<String, Object> relationMap) {

    }
}
