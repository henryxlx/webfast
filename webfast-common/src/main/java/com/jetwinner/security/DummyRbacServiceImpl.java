package com.jetwinner.security;

import java.util.HashSet;

/**
 * @author xulixin
 */
public class DummyRbacServiceImpl implements RbacService {

    @Override
    public BaseAppUser getBaseAppUserByUsername(String username) {
        return new BaseAppUser();
    }

    @Override
    public UserHasRoleAndPermission getRoleAndPermissionByUsername(String username) {
        return new UserHasRoleAndPermission(new BaseAppUser(),
                new HashSet<>(0), new HashSet<>(0));
    }
}
