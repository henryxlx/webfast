package com.jetwinner.security;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
public interface UserAccessControlService {

    void doLoginCheck(String username, String password) throws Exception;

    boolean isLoggedIn();

    Object getCurrentUser();

    boolean hasRole(String roleName);

    void setEncryptPassword(BaseAppUser user);

    String getSavedUrlBeforeLogin(HttpServletRequest request);
}
