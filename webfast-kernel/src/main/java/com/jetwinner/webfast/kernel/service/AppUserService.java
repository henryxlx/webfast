package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserService {

    void checkPutMeIntoShrioAccountService();

    AppUser getByUsername(String username);

    Set<String> findRolesByUsername(String username);

    Set<String> findPermissionsByUsername(String username);

    void register(Map<String, Object> user);
}
