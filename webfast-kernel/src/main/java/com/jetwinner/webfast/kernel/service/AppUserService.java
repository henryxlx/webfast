package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserService {

    AppUser getByUsername(String username);

    Set<String> findRolesByUsername(String username);

    Set<String> findPermissionsByUsername(String username);

    void register(AppUser user);
}
