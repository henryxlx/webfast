package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppMessageConversationDao {

    void updateConversation(Integer id, Map<String, Object> entityMap);

    AppModelMessageConversation addConversation(Map<String, Object> entityMap);

    AppModelMessageConversation getConversationByFromIdAndToId(Integer fromId, Integer toId);
}
