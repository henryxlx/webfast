package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.BaseAppUser;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xulixin
 */
@Component
public class ShiroAccountServiceImpl implements ShiroAccountService {

    private BaseAppUserService userService;
    private DummyShiroAccountServiceImpl dummyShiroAccountService;

    public ShiroAccountServiceImpl() {
        this.dummyShiroAccountService = new DummyShiroAccountServiceImpl();
    }

    @Override
    public void setUserService(BaseAppUserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseAppUser getBaseAppUserByUsername(String username) {
        return userService == null ? dummyShiroAccountService.getBaseAppUserByUsername(username)
                : userService.getBaseAppUserByUsername(username);
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        return userService == null ? dummyShiroAccountService.findRolesByUsername(username)
                : userService.findRolesByUsername(username);
    }

    @Override
    public Set<String> findPermissionsByUsername(String username) {
        return userService == null ? dummyShiroAccountService.findPermissionsByUsername(username)
                : userService.findPermissionsByUsername(username);
    }

}
