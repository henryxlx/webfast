package com.jetwinner.webfast.kernel.model;

import com.jetwinner.webfast.kernel.AppUser;

/**
 * @author x230-think-joomla
 * @date 2016/1/8
 */
public class AppModelMessage {

    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Long createdTime;
    private AppUser createdUser;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public AppUser getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(AppUser createdUser) {
        this.createdUser = createdUser;
    }
}
