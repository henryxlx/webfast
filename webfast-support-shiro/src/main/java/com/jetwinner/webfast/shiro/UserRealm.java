package com.jetwinner.webfast.shiro;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.RbacService;
import com.jetwinner.security.UserHasRoleAndPermission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author xulixin
 */
public class UserRealm extends AuthorizingRealm {

    private final RbacService rbacService;

    public UserRealm(RbacService userService) {
        this.rbacService = userService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        BaseAppUser user = rbacService.getBaseAppUserByUsername(username);
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
        UserHasRoleAndPermission roleAndPermission = rbacService.getRoleAndPermissionByUsername(user.getUsername());
        authorizationInfo.setRoles(roleAndPermission.getRoles());
        authorizationInfo.setStringPermissions(roleAndPermission.getPermissions());
        return authorizationInfo;
    }

}
