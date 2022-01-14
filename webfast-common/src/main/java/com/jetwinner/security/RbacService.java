package com.jetwinner.security;

import java.util.Set;

/**
 * @author xulixin
 */
public interface RbacService {

    default void setUserService(BaseAppUserService userService) {}

    BaseAppUser getBaseAppUserByUsername(String username);

    Set<String> findRolesByUsername(String username);

    Set<String> findPermissionsByUsername(String username);

}
