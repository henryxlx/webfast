package com.jetwinner.security;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xulixin
 */
public class DummyRbacServiceImpl implements RbacService {

    @Override
    public BaseAppUser getBaseAppUserByUsername(String username) {
        return new BaseAppUser();
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        return new HashSet<>(0);
    }

    @Override
    public Set<String> findPermissionsByUsername(String username) {
        return new HashSet<>(0);
    }
}
