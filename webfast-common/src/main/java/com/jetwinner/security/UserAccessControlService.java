package com.jetwinner.security;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
public interface UserAccessControlService {

    void doLoginCheck(String username, String password, boolean rememberMe) throws Exception;

    boolean isLoggedIn();

    Object getCurrentUser();

    boolean hasRole(String roleName);

    boolean hasAnyRole(String... roleNames);

    boolean isGranted(String permissionKey);

    void setEncryptPassword(BaseAppUser user);

    String getSavedUrlBeforeLogin(HttpServletRequest request);

    default boolean checkPassword(String password, String userLoginPassword) {
        return true;
    }
}
