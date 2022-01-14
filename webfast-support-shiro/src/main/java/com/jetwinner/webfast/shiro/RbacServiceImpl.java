package com.jetwinner.webfast.shiro;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.BaseAppUserService;
import com.jetwinner.security.DummyRbacServiceImpl;
import com.jetwinner.security.RbacService;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xulixin
 */
@Component
public class RbacServiceImpl implements RbacService {

    private BaseAppUserService userService;
    private final DummyRbacServiceImpl dummyRbacService;

    public RbacServiceImpl() {
        this.dummyRbacService = new DummyRbacServiceImpl();
    }

    @Override
    public void setUserService(BaseAppUserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseAppUser getBaseAppUserByUsername(String username) {
        return userService == null ? dummyRbacService.getBaseAppUserByUsername(username)
                : userService.getBaseAppUserByUsername(username);
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        return userService == null ? dummyRbacService.findRolesByUsername(username)
                : userService.findRolesByUsername(username);
    }

    @Override
    public Set<String> findPermissionsByUsername(String username) {
        return userService == null ? dummyRbacService.findPermissionsByUsername(username)
                : userService.findPermissionsByUsername(username);
    }

}
