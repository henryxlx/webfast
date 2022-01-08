package com.jetwinner.webfast.kernel;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
public class AppUser extends BaseAppUser {

    public static final AppUser getCurrentUser(HttpServletRequest request) {
        AppUser user = (AppUser) request.getAttribute(MODEL_VAR_NAME);
        return user != null ? user : new AppUser();
    }

    private String verifiedMobile;
    private String title;
    private String tags;
    private String type;
    private String smallAvatar;
    private String mediumAvatar;
    private String largeAvatar;
    private Integer emailVerified;
    private Integer setup;
    private String roles;
    private Integer promoted;
    private Integer promotedTime;
    private Integer locked;
    private Integer lockDeadline;
    private Integer consecutivePasswordErrorTimes;
    private Integer lastPasswordFailTime;
    private Integer loginTime;
    private String loginIp;
    private String loginSessionId;
    private Integer approvalTime;
    private String approvalStatus;
    private Integer newMessageNum;
    private Integer newNotificationNum;
    private String createdIp;
    private Long createdTime;

    public String getVerifiedMobile() {
        return verifiedMobile;
    }

    public void setVerifiedMobile(String verifiedMobile) {
        this.verifiedMobile = verifiedMobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmallAvatar() {
        return smallAvatar;
    }

    public void setSmallAvatar(String smallAvatar) {
        this.smallAvatar = smallAvatar;
    }

    public String getMediumAvatar() {
        return mediumAvatar;
    }

    public void setMediumAvatar(String mediumAvatar) {
        this.mediumAvatar = mediumAvatar;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public Integer getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Integer getSetup() {
        return setup;
    }

    public void setSetup(Integer setup) {
        this.setup = setup;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getPromoted() {
        return promoted;
    }

    public void setPromoted(Integer promoted) {
        this.promoted = promoted;
    }

    public Integer getPromotedTime() {
        return promotedTime;
    }

    public void setPromotedTime(Integer promotedTime) {
        this.promotedTime = promotedTime;
    }

    @Override
    public Integer getLocked() {
        return locked;
    }

    @Override
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getLockDeadline() {
        return lockDeadline;
    }

    public void setLockDeadline(Integer lockDeadline) {
        this.lockDeadline = lockDeadline;
    }

    public Integer getConsecutivePasswordErrorTimes() {
        return consecutivePasswordErrorTimes;
    }

    public void setConsecutivePasswordErrorTimes(Integer consecutivePasswordErrorTimes) {
        this.consecutivePasswordErrorTimes = consecutivePasswordErrorTimes;
    }

    public Integer getLastPasswordFailTime() {
        return lastPasswordFailTime;
    }

    public void setLastPasswordFailTime(Integer lastPasswordFailTime) {
        this.lastPasswordFailTime = lastPasswordFailTime;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }

    public Integer getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Integer approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getNewMessageNum() {
        return newMessageNum;
    }

    public void setNewMessageNum(Integer newMessageNum) {
        this.newMessageNum = newMessageNum;
    }

    public Integer getNewNotificationNum() {
        return newNotificationNum;
    }

    public void setNewNotificationNum(Integer newNotificationNum) {
        this.newNotificationNum = newNotificationNum;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}
