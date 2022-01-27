package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.model.AppModelMessageRelation;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppMessageRelationDao {

    int addRelation(Map<String, Object> relationMap);

    int deleteConversationMessage(Integer conversationId, Integer messageId);

    int deleteRelationByConversationId(Integer conversationId);

    int getRelationCountByConversationId(Integer conversationId);

    AppModelMessageRelation getRelationByConversationIdAndMessageId(Integer conversationId, Integer messageId);

    int updateRelationIsReadByConversationId(Integer conversationId, Map<String, Object> fields);

    List<AppModelMessageRelation> findRelationsByConversationId(Integer conversationId, int start, int limit);
}
