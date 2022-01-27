package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;

import java.util.List;

public interface AppMessageService {

    int getUserConversationCount(Integer userId);

    List<AppModelMessageConversation> findUserConversations(Integer userId, int start, int limit);

    void clearUserNewMessageCounter(Integer userId);

    void sendMessage(Integer fromUserId, Integer toUserId, Object content);
}
