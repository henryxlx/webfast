package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xulixin
 */
public class DummyShiroAccountServiceImpl implements ShiroAccountService {

    @Override
    public AppUser getByUsername(String username) {
        return new AppUser();
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
