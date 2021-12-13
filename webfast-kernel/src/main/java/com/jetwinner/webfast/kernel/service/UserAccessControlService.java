package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.exception.ActionGraspException;

/**
 * @author xulixin
 */
public interface UserAccessControlService {

    void doLoginCheck(String username, String password) throws ActionGraspException;

    boolean isLoggedIn();

    Object getCurrentUser();

    boolean hasRole(String roleName);
}
