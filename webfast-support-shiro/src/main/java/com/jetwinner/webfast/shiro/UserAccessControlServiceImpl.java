package com.jetwinner.webfast.shiro;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.UserAccessControlService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
@Service
public class UserAccessControlServiceImpl implements UserAccessControlService {

    @Override
    public void doLoginCheck(String username, String password, boolean rememberMe) throws Exception {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (IncorrectCredentialsException e) {
            throw new Exception("密码错误");
        } catch (ExcessiveAttemptsException e) {
            throw new Exception("登录失败次数过多");
        } catch (LockedAccountException e) {
            throw new Exception("用户被锁定");
        } catch (DisabledAccountException e) {
            throw new Exception("帐号已被禁用");
        } catch (ExpiredCredentialsException e) {
            throw new Exception("帐号已过期");
        } catch (UnknownAccountException e) {
            throw new Exception("帐号不存在");
        } catch (UnauthorizedException e) {
            throw new Exception("您没有得到相应的授权！");
        } catch (Exception e) {
            throw new Exception("未知原因登录失败，请联系管理员");
        }
    }

    @Override
    public boolean isLoggedIn() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public Object getCurrentUser() {
        return SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public boolean hasRole(String roleName) {
        return SecurityUtils.getSubject().hasRole(roleName);
    }

    @Override
    public boolean hasAnyRole(String... roleNames) {
        if (roleNames != null && roleNames.length > 0) {
            for (String roleName : roleNames) {
                if (SecurityUtils.getSubject().hasRole(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isGranted(String permissionKey) {
        return SecurityUtils.getSubject().isPermitted(permissionKey);
    }

    @Override
    public void setEncryptPassword(BaseAppUser user) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String password = PasswordEncoder.encodePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
    }

    @Override
    public String getSavedUrlBeforeLogin(HttpServletRequest request) {
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        return savedRequest != null ? savedRequest.getRequestUrl() : null;
    }
}
