package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.RbacService;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDao userDao;
    private final DataSourceConfig dataSourceConfig;
    private final RbacService rbacService;

    public AppUserServiceImpl(AppUserDao userDao,
                              DataSourceConfig dataSourceConfig,
                              RbacService rbacService) {

        this.userDao = userDao;
        this.dataSourceConfig = dataSourceConfig;
        this.rbacService = rbacService;
    }

    @PostConstruct
    @Override
    public void checkPutMeIntoRbacService() {
        if (!dataSourceConfig.getDataSourceDisabled()) {
            rbacService.setUserService(this);
        }
    }

    @Override
    public BaseAppUser getBaseAppUserByUsername(String username) {
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
    public AppUser getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public void register(Map<String, Object> user) {
        userDao.insert(user);
    }

    @Override
    public Map<String, AppUser> findUsersByIds(Set<Object> userIds) {
        return ArrayToolkitOnJava8.index(userDao.findByIds(userIds), AppUser::getId);
    }

    @Override
    public AppUser getUser(Object id) {
        return userDao.getUser(id);
    }

    @Override
    public int searchUserCount(Map<String, Object> conditions) {
        return userDao.searchUserCount(conditions);
    }

    @Override
    public List<AppUser> searchUsers(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit) {
        return userDao.searchUsers(conditions, orderByBuilder, start, limit);
    }

    @Override
    public Map<String, Object> getUserProfile(Integer id) {
        return userDao.getProfile(id);
    }

    @Override
    public boolean existEmail(String value) {
        return userDao.countForEmail(value) > 0;
    }
}
