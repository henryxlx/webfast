package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppMessageConversationDao;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppMessageConversationDaoImpl extends FastJdbcDaoSupport implements AppMessageConversationDao {

    @Override
    public void updateConversation(Integer id, Map<String, Object> entityMap) {

    }

    @Override
    public AppModelMessageConversation addConversation(Map<String, Object> entityMap) {
        return null;
    }

    @Override
    public AppModelMessageConversation getConversationByFromIdAndToId(Integer fromId, Integer toId) {
        return null;
    }
}
