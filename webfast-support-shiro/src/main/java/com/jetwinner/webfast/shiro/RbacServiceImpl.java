package com.jetwinner.webfast.shiro;

import com.jetwinner.security.*;
import org.springframework.stereotype.Component;

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
    public UserHasRoleAndPermission getRoleAndPermissionByUsername(String username) {
        return userService == null ? dummyRbacService.getRoleAndPermissionByUsername(username)
                : userService.getRoleAndPermissionByUsername(username);
    }
}
