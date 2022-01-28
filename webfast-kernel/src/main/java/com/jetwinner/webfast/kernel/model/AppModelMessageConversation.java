package com.jetwinner.webfast.kernel.model;

/**
 *
 * @author x230-think-joomla
 * @date 2016/1/8
 */
public class AppModelMessageConversation {

    private Integer id;
    private Integer fromId;
    private Integer toId;
    private Integer messageNum;
    private Integer latestMessageUserId;
    private Long latestMessageTime;
    private String latestMessageContent;
    private Integer unreadNum;
    private Long createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    public Integer getLatestMessageUserId() {
        return latestMessageUserId;
    }

    public void setLatestMessageUserId(Integer latestMessageUserId) {
        this.latestMessageUserId = latestMessageUserId;
    }

    public Long getLatestMessageTime() {
        return latestMessageTime;
    }

    public void setLatestMessageTime(Long latestMessageTime) {
        this.latestMessageTime = latestMessageTime;
    }

    public String getLatestMessageContent() {
        return latestMessageContent;
    }

    public void setLatestMessageContent(String latestMessageContent) {
        this.latestMessageContent = latestMessageContent;
    }

    public Integer getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(Integer unreadNum) {
        this.unreadNum = unreadNum;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}
