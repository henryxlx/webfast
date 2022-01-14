package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.RbacService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xulixin
 */
@Deprecated
@Component
@PropertySource("classpath:test/buildin-user.properties")
@ConfigurationProperties(prefix = "prop")
public class FakeAppUserServiceImpl implements RbacService {

    private List<BaseAppUser> users = new ArrayList<>();

    public List<BaseAppUser> getUsers() {
        return users;
    }

    public void setUsers(List<BaseAppUser> users) {
        this.users = users;
    }

    @Override
    public BaseAppUser getBaseAppUserByUsername(String username) {
        BaseAppUser appUser = new BaseAppUser();
        if (users != null) {
            for (BaseAppUser user : users) {
                if (username.equals(user.getUsername())) {
                    appUser.setUsername(user.getUsername());
                    appUser.setPassword(user.getPassword());
                    appUser.setSalt(user.getSalt());
                    appUser.setLocked(0);
                    break;
                }
            }
        }

        return appUser;
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        Set<String> roles = new HashSet<>();
        if ("admin".equals(username) || "super".equals(username)) {
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
