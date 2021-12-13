package com.jetwinner.webfast.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import com.jetwinner.webfast.kernel.BaseAppUser;
import com.jetwinner.webfast.kernel.service.AppUserService;

import java.util.Set;

/**
 * @author xulixin
 */
public class UserRealm extends AuthorizingRealm {

    private final AppUserService appUserService;

    public UserRealm(AppUserService userService) {
        this.appUserService = userService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        BaseAppUser user = appUserService.getByUsername(username);
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        // 账号被锁定
        if (user.getLocked() != 0) {
            throw new LockedAccountException();
        }
        String salt = user.getSalt();

        return new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(salt), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        BaseAppUser user = (BaseAppUser) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = appUserService.findRolesByUsername(user.getUsername());
        authorizationInfo.setRoles(roles);
        Set<String> permissions = appUserService.findPermissionsByUsername(user.getUsername());
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

}
