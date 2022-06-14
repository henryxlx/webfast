package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;

import java.util.List;
import java.util.Map;

public interface AppMessageService {

    int getUserConversationCount(Integer userId);

    List<AppModelMessageConversation> findUserConversations(Integer userId, int start, int limit);

    void clearUserNewMessageCounter(Integer userId);

    void sendMessage(Integer fromUserId, Integer toUserId, Object content);

    int searchMessagesCount(Map<String, Object> conditions);

    List<AppModelMessage> searchMessages(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                         int start, int limit);

    AppModelMessageConversation getConversation(Integer conversationId);

    int deleteConversationMessage(Integer conversationId, Integer messageId);

    int getConversationMessageCount(Integer conversationId);

    int deleteConversation(Integer conversationId);

    int  markConversationRead(Integer conversationId);

    List<AppModelMessage> findConversationMessages(Integer conversationId, int start, int limit);

    boolean deleteMessagesByIds(String[] ids);

    AppModelMessageConversation getConversationByFromIdAndToId(Integer fromId, Integer toId);
}
