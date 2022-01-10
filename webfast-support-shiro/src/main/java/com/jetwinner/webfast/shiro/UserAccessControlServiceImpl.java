package com.jetwinner.webfast.shiro;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.webfast.kernel.exception.ActionGraspException;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.stereotype.Service;

/**
 * @author xulixin
 */
@Service
public class UserAccessControlServiceImpl implements UserAccessControlService {

    @Override
    public void doLoginCheck(String username, String password) throws ActionGraspException {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (IncorrectCredentialsException e) {
            throw new ActionGraspException("密码错误");
        } catch (ExcessiveAttemptsException e) {
            throw new ActionGraspException("登录失败次数过多");
        } catch (LockedAccountException e) {
            throw new ActionGraspException("用户被锁定");
        } catch (DisabledAccountException e) {
            throw new ActionGraspException("帐号已被禁用");
        } catch (ExpiredCredentialsException e) {
            throw new ActionGraspException("帐号已过期");
        } catch (UnknownAccountException e) {
            throw new ActionGraspException("帐号不存在");
        } catch (UnauthorizedException e) {
            throw new ActionGraspException("您没有得到相应的授权！");
        } catch (Exception e) {
            throw new ActionGraspException("未知原因登录失败，请联系管理员");
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
    public void setEncryptPassword(BaseAppUser user) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String password = PasswordEncoder.encodePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
    }
}
