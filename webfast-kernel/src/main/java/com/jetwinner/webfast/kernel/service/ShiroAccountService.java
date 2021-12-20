package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.Set;

/**
 * @author xulixin
 */
public interface ShiroAccountService {

    default void setUserService(AppUserService userService) {}

    AppUser getByUsername(String username);

    Set<String> findRolesByUsername(String username);

    Set<String> findPermissionsByUsername(String username);

}
