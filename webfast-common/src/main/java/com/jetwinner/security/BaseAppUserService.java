package com.jetwinner.security;

import java.util.Set;

/**
 * @author xulixin
 */
public interface BaseAppUserService {

    void checkPutMeIntoRbacService();

    BaseAppUser getBaseAppUserByUsername(String username);

    Set<String> findRolesByUsername(String username);

    Set<String> findPermissionsByUsername(String username);

}
