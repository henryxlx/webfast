package com.jetwinner.webfast.kernel.service;

import com.jetwinner.toolbag.HtmlToolkit;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.dao.AppMessageConversationDao;
import com.jetwinner.webfast.kernel.dao.AppMessageDao;
import com.jetwinner.webfast.kernel.dao.AppMessageRelationDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import com.jetwinner.webfast.kernel.model.AppModelMessageRelation;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Service
public class AppMessageServiceImpl implements AppMessageService {

    private static final String RELATION_ISREAD_ON = "1";
    private static final String RELATION_ISREAD_OFF = "0";

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
                    .add("fromId", fromId)
                    .add("toId", toId)
                    .add("messageNum", 1)
                    .add("latestMessageUserId", message.getFromId())
                    .add("latestMessageContent", message.getContent())
                    .add("latestMessageTime", message.getCreatedTime())
                    .add("unreadNum", 0)
                    .add("createdTime", System.currentTimeMillis()).toMap();
            conversation = conversationDao.addConversation(fields);
        }

        Map<String, Object> relation = new ParamMap()
                .add("conversationId", conversation.getId())
                .add("messageId", message.getId())
                .add("isRead", "0").toMap();
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
                    .add("createdTime", System.currentTimeMillis()).toMap();
            conversation = conversationDao.addConversation(fields);
        }
        Map<String, Object> relation = new ParamMap()
                .add("conversationId", conversation.getId())
                .add("messageId", message.getId())
                .add("isRead", "0").toMap();
        relationDao.addRelation(relation);
    }

    @Override
    public AppModelMessageConversation getConversation(Integer conversationId) {
        return conversationDao.getConversation(conversationId);
    }

    @Override
    public int deleteConversationMessage(Integer conversationId, Integer messageId) {
        AppModelMessageRelation relation = relationDao.getRelationByConversationIdAndMessageId(conversationId, messageId);
        AppModelMessageConversation conversation = conversationDao.getConversation(conversationId);

        if (RELATION_ISREAD_OFF.equals(relation.getIsRead())) {
            safelyUpdateConversationMessageNum(conversation);
            safelyUpdateConversationUnreadNum(conversation);
        } else {
            safelyUpdateConversationMessageNum(conversation);
        }

        int nums = relationDao.deleteConversationMessage(conversationId, messageId);
        int relationCount = relationDao.getRelationCountByConversationId(conversationId);
        if (relationCount == 0) {
            conversationDao.deleteConversation(conversationId);
        }
        return nums;
    }

    @Override
    public int getConversationMessageCount(Integer conversationId) {
        return relationDao.getRelationCountByConversationId(conversationId);
    }

    @Override
    public int deleteConversation(Integer conversationId) {
        relationDao.deleteRelationByConversationId(conversationId);
        return conversationDao.deleteConversation(conversationId);
    }

    private void safelyUpdateConversationMessageNum(AppModelMessageConversation conversation) {
        if (conversation.getMessageNum() <= 0) {
            conversationDao.updateConversation(conversation.getId(),
                    new ParamMap().add("messageNum", 0).toMap());
        } else {
            conversationDao.updateConversation(conversation.getId(),
                    new ParamMap().add("messageNum", conversation.getMessageNum() - 1).toMap());
        }
    }

    private void safelyUpdateConversationUnreadNum(AppModelMessageConversation conversation) {
        if (conversation.getMessageNum() <= 0) {
            conversationDao.updateConversation(conversation.getId(),
                    new ParamMap().add("unreadNum", 0).toMap());
        } else {
            conversationDao.updateConversation(conversation.getId(),
                    new ParamMap().add("unreadNum", conversation.getUnreadNum() - 1).toMap());
        }
    }

    @Override
    public int  markConversationRead(Integer conversationId) {
        AppModelMessageConversation conversation = conversationDao.getConversation(conversationId);
        if (conversation == null) {
            throw new RuntimeGoingException(String.format("私信会话#%d不存在。", conversationId));
        }
        int updatedConversation = conversationDao.updateConversation(conversation.getId(), new ParamMap().add("unreadNum", 0).toMap());
        relationDao.updateRelationIsReadByConversationId(conversationId, new ParamMap().add("isRead", "1").toMap());
        return updatedConversation;
    }

    @Override
    public List<AppModelMessage> findConversationMessages(Integer conversationId, int start, int limit) {
        List<AppModelMessageRelation> relations = relationDao.findRelationsByConversationId(conversationId, start, limit);
        List<AppModelMessage> messages = messageDao.findMessagesByIds(
                relations.stream().map(AppModelMessageRelation::getMessageId).collect(Collectors.toList()));
        sortMessages(messages);
        return messages;
    }

    @Override
    public boolean deleteMessagesByIds(String[] ids) {
        if (ids == null || ids.length == 0) {
            throw new RuntimeGoingException("Please select message item !");
        }
        for(String id : ids) {
            int messageId = ValueParser.parseInt(id);
            AppModelMessage message = messageDao.getMessage(messageId);
            AppModelMessageConversation conversation =
                    conversationDao.getConversationByFromIdAndToId(message.getFromId(), message.getToId());
            if(conversation != null){
                deleteConversationMessage(conversation.getId(), message.getId());
            }

            conversation = conversationDao.getConversationByFromIdAndToId(message.getToId(), message.getFromId());
            if(conversation != null){
                deleteConversationMessage(conversation.getId(), message.getId());
            }
            messageDao.deleteMessage(messageId);
        }
        return true;
    }

    private void sortMessages(List<AppModelMessage> messages) {
        messages.sort((a, b) -> Long.compare(b.getCreatedTime(), a.getCreatedTime()));
    }
}
