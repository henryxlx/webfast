package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xulixin
 */
@Primary
@Service
public class FakeAppUserServiceImpl implements AppUserService {

    @Override
    public AppUser getByUsername(String username) {
        AppUser appUser = new AppUser();
        appUser.setUsername("admin");
        appUser.setPassword("xlcCgpKVU0WyymojNTUwzA1IOmYB8KitkBF54DkzWH8=");
        appUser.setSalt("74rLDmaXBZ/J76iSchcNZA==");
        appUser.setLocked(0);
        return appUser;
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        Set<String> roles = new HashSet<>();
        if ("admin".equals(username)) {
            roles.add("ROLE_BACKEND");
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_SUPER_ADMIN");
        } else {
            roles.add("ROLE_GUEST");
        }
        return roles;
    }

    @Override
    public Set<String> findPermissionsByUsername(String username) {
        return new HashSet<>(0);
    }
}
