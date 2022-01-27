package com.jetwinner.webfast.kernel.service;

import com.jetwinner.toolbag.HtmlToolkit;
import com.jetwinner.webfast.kernel.dao.AppMessageConversationDao;
import com.jetwinner.webfast.kernel.dao.AppMessageDao;
import com.jetwinner.webfast.kernel.dao.AppMessageRelationDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppMessageServiceImpl implements AppMessageService {

    private final AppUserService userService;
    private final AppMessageDao messageDao;
    private final AppMessageConversationDao conversationDao;
    private final AppMessageRelationDao relationDao;

    public AppMessageServiceImpl(AppUserService userService,
                                 AppMessageDao messageDao,
                                 AppMessageConversationDao conversationDao,
                                 AppMessageRelationDao relationDao) {

        this.userService = userService;
        this.messageDao = messageDao;
        this.conversationDao = conversationDao;
        this.relationDao = relationDao;
    }

    @Override
    public int getUserConversationCount(Integer userId) {
        return conversationDao.getConversationCountByToId(userId);
    }

    @Override
    public List<AppModelMessageConversation> findUserConversations(Integer userId, int start, int limit) {
        return conversationDao.findConversationsByToId(userId, start, limit);
    }

    @Override
    public void clearUserNewMessageCounter(Integer userId) {
        userService.clearUserCounter(userId, "newMessageNum");
    }

    @Override
    public void sendMessage(Integer fromUserId, Integer toUserId, Object content) {
        if (fromUserId.intValue() == toUserId.intValue()) {
            throw new RuntimeGoingException("抱歉,不允许给自己发送私信!");
        }

        AppModelMessage message = addMessage(fromUserId, toUserId, content);
        prepareConversationAndRelationForSender(message, toUserId, fromUserId);
        prepareConversationAndRelationForReceiver(message, fromUserId, toUserId);
        userService.waveUserCounter(toUserId, "newMessageNum", 1);
    }

    @Override
    public int searchMessagesCount(Map<String, Object> conditions) {
        return messageDao.searchMessagesCount(conditions);
    }

    @Override
    public List<AppModelMessage> searchMessages(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                                int start, int limit) {

        return messageDao.searchMessages(conditions, orderByBuilder, start, limit);
    }

    private AppModelMessage addMessage(Integer fromId, Integer toId, Object content) {
        Map<String, Object> message = new ParamMap()
                .add("fromId", fromId)
                .add("toId", toId)
                .add("content", HtmlToolkit.purifyHtml(String.valueOf(content)))
                .add("createdTime", System.currentTimeMillis())
                .toMap();
        int id = messageDao.addMessage(message);
        return messageDao.getMessage(id);
    }

    private void prepareConversationAndRelationForSender(AppModelMessage message, Integer toId, Integer fromId) {
        AppModelMessageConversation conversation = conversationDao.getConversationByFromIdAndToId(toId, fromId);
        if (conversation != null) {
            conversationDao.updateConversation(conversation.getId(), new ParamMap()
                    .add("messageNum", conversation.getMessageNum() + 1)
                    .add("latestMessageUserId", message.getFromId())
                    .add("latestMessageContent", message.getContent())
                    .add("latestMessageTime", message.getCreatedTime()).toMap());
        } else {
            Map<String, Object> fields = new ParamMap()
                    .add("fromId", toId)
                    .add("toId", fromId)
                    .add("messageNum", 1)
                    .add("latestMessageUserId", message.getFromId())
                    .add("latestMessageContent", message.getContent())
                    .add("latestMessageTime", message.getCreatedTime())
                    .add("unreadNum", 0)
                    .add("createdTime", new Date()).toMap();
            conversation = conversationDao.addConversation(fields);
        }

        Map<String, Object> relation = new ParamMap()
                .add("conversationId", conversation.getId())
                .add("messageId", message.getId())
                .add("isRead", 0).toMap();
        relationDao.addRelation(relation);
    }

    private void prepareConversationAndRelationForReceiver(AppModelMessage message, Integer fromId, Integer toId) {
        AppModelMessageConversation conversation = conversationDao.getConversationByFromIdAndToId(fromId, toId);
        if (conversation != null) {
            conversationDao.updateConversation(conversation.getId(), new ParamMap()
                    .add("messageNum", conversation.getMessageNum() + 1)
                    .add("latestMessageUserId", message.getFromId())
                    .add("latestMessageContent", message.getContent())
                    .add("latestMessageTime", message.getCreatedTime())
                    .add("unreadNum", conversation.getUnreadNum() + 1).toMap());
        } else {
            Map<String, Object> fields = new ParamMap()
                    .add("fromId", fromId)
                    .add("toId", toId)
                    .add("messageNum", 1)
                    .add("latestMessageUserId", message.getFromId())
                    .add("latestMessageContent", message.getContent())
                    .add("latestMessageTime", message.getCreatedTime())
                    .add("unreadNum", 1)
                    .add("createdTime", new Date()).toMap();
            conversation = conversationDao.addConversation(fields);
        }
        Map<String, Object> relation = new ParamMap()
                .add("conversationId", conversation.getId())
                .add("messageId", message.getId())
                .add("isRead", 0).toMap();
        relationDao.addRelation(relation);
    }
}
