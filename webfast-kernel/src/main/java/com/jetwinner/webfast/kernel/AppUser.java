package com.jetwinner.webfast.kernel;

import com.jetwinner.security.BaseAppUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
public class AppUser extends BaseAppUser {

    public static AppUser getCurrentUser(HttpServletRequest request) {
        AppUser user = (AppUser) request.getAttribute(MODEL_VAR_NAME);
        return user != null ? user : new AppUser();
    }

    public static AppUser getCurrentUser(Map<String, Object> map) {
        AppUser user = (AppUser) map.get(MODEL_VAR_NAME);
        return user != null ? user : new AppUser();
    }

    public static Map<String, Object> putCurrentUser(Map<String, Object> map, AppUser currentUser) {
        map.put(MODEL_VAR_NAME, currentUser);
        return map;
    }

    public static Map<String, Object> putCurrentUser(Map<String, Object> map, HttpServletRequest request) {
        AppUser user = getCurrentUser(request);
        map.put(MODEL_VAR_NAME, user);
        return map;
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
    private String permissionData;
    private Integer promoted;
    private Long promotedTime;
    private Integer locked;
    private Integer lockDeadline;
    private Integer consecutivePasswordErrorTimes;
    private Long lastPasswordFailTime;
    private Long loginTime;
    private String loginIp;
    private String loginSessionId;
    private Long approvalTime;
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

    public String getPermissionData() {
        return permissionData;
    }

    public void setPermissionData(String permissionData) {
        this.permissionData = permissionData;
    }

    public Integer getPromoted() {
        return promoted;
    }

    public void setPromoted(Integer promoted) {
        this.promoted = promoted;
    }

    public Long getPromotedTime() {
        return promotedTime;
    }

    public void setPromotedTime(Long promotedTime) {
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

    public Long getLastPasswordFailTime() {
        return lastPasswordFailTime;
    }

    public void setLastPasswordFailTime(Long lastPasswordFailTime) {
        this.lastPasswordFailTime = lastPasswordFailTime;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
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

    public Long getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Long approvalTime) {
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

    public boolean hasAnyRole(String... roleNames) {
        String userRoles = getRoles();
        if (roleNames != null && userRoles != null) {
            for (String roleName : roleNames) {
                if (userRoles.indexOf(roleName) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
