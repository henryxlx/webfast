package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppMessageConversationDao {

    AppModelMessageConversation getConversation(Integer id);

    Integer getConversationCountByToId(Integer userId);

    List<AppModelMessageConversation> findConversationsByToId(Integer toUserId, int start, int limit);

    AppModelMessageConversation getConversationByFromIdAndToId(Integer fromId, Integer toId);

    int updateConversation(Integer id, Map<String, Object> fields);

    AppModelMessageConversation addConversation(Map<String, Object> fields);

    int deleteConversation(Integer id);
}
