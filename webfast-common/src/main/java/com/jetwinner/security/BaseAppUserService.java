package com.jetwinner.security;

/**
 * @author xulixin
 */
public interface BaseAppUserService {

    void checkPutMeIntoRbacService();
    
    BaseAppUser getBaseAppUserByUsername(String username);

    UserHasRoleAndPermission getRoleAndPermissionByUsername(String username);
}
