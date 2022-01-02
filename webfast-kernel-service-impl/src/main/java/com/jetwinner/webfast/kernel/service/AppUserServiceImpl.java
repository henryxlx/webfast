package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.ArrayToolkitJava8Util;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDao userDao;
    private final DataSourceConfig dataSourceConfig;
    private final ShiroAccountService shiroAccountService;

    public AppUserServiceImpl(AppUserDao userDao,
                              DataSourceConfig dataSourceConfig,
                              ShiroAccountService shiroAccountService) {

        this.userDao = userDao;
        this.dataSourceConfig = dataSourceConfig;
        this.shiroAccountService = shiroAccountService;
    }

    @PostConstruct
    @Override
    public void checkPutMeIntoShrioAccountService() {
        if (!dataSourceConfig.getDataSourceDisabled()) {
            shiroAccountService.setUserService(this);
        }
    }

    @Override
    public AppUser getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public Set<String> findRolesByUsername(String username) {
        AppUser user = userDao.getByUsername(username);
        return toRoleSet(user.getRoles());
    }

    private Set<String> toRoleSet(String strRoles) {
        String[] roleStrArray = strRoles.split("\\|");
        HashSet<String> roles = new HashSet<>();
        if (roleStrArray.length < 1) {
            return roles;
        }
        for (String role : roleStrArray) {
            if (EasyStringUtil.isNotBlank(role)) {
                roles.add(role);
            }
        }
        return roles;
    }

    @Override
    public Set<String> findPermissionsByUsername(String username) {
        return new HashSet<>(0);
    }

    @Override
    public void register(Map<String, Object> user) {
        userDao.insert(user);
    }

    @Override
    public Map<String, AppUser> findUsersByIds(Set<Object> userIds) {
        return ArrayToolkitJava8Util.index(userDao.findByIds(userIds), AppUser::getId);
    }

    @Override
    public AppUser getUser(Object id) {
        return userDao.getUser(id);
    }
}
