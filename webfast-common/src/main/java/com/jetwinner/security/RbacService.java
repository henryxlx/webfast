package com.jetwinner.security;

/**
 * @author xulixin
 */
public interface RbacService {

    default void setUserService(BaseAppUserService userService) {}

    BaseAppUser getBaseAppUserByUsername(String username);

    UserHasRoleAndPermission getRoleAndPermissionByUsername(String username);

}
