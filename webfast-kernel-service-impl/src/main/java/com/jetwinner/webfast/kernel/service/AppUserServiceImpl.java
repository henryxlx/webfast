package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.RbacService;
import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.security.UserHasRoleAndPermission;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.util.ArrayUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppRoleDao;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import com.jetwinner.webfast.kernel.dao.AppUserProfileDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelRole;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDao userDao;
    private final AppUserProfileDao userProfileDao;
    private final AppRoleDao roleDao;
    private final DataSourceConfig dataSourceConfig;
    private final RbacService rbacService;
    private final UserAccessControlService userAccessControlService;

    public AppUserServiceImpl(AppUserDao userDao,
                              AppUserProfileDao userProfileDao,
                              AppRoleDao roleDao,
                              DataSourceConfig dataSourceConfig,
                              RbacService rbacService,
                              UserAccessControlService userAccessControlService) {

        this.userDao = userDao;
        this.userProfileDao = userProfileDao;
        this.roleDao = roleDao;
        this.dataSourceConfig = dataSourceConfig;
        this.rbacService = rbacService;
        this.userAccessControlService = userAccessControlService;
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
    public UserHasRoleAndPermission getRoleAndPermissionByUsername(String username) {
        AppUser user = userDao.getByUsername(username);
        Set<String> roles = strToSet(user != null ? user.getRoles() : "");
        Set<String> permissions = toPermissionSet(user, roles);
        return new UserHasRoleAndPermission(user, roles, permissions);
    }

    private Set<String> strToSet(String strData) {
        String[] strArray = strData != null ? strData.split("\\|") : new String[0];
        HashSet<String> set = new HashSet<>();
        for (String str : strArray) {
            if (EasyStringUtil.isNotBlank(str)) {
                set.add(str);
            }
        }
        return set;
    }

    private Set<String> toPermissionSet(AppUser user, Set<String> userRoles) {
        Set<String> permissions = new HashSet<>();
        Set<String> userPermissions = strToSet(user.getPermissionData());
        if (!userPermissions.isEmpty()) {
            permissions.addAll(userPermissions);
        }
        List<AppModelRole> roles = roleDao.listAll().stream()
                .filter(r -> userRoles.contains(r.getRoleName())).collect(Collectors.toList());
        roles.forEach(role -> {
            Set<String> rolePermissions = strToSet(role.getPermissionData());
            if (!rolePermissions.isEmpty()) {
                permissions.addAll(rolePermissions);
            }
        });
        return permissions;
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public AppUser register(Map<String, Object> registration) {
        return register(registration, "default");
    }

    public AppUser register(Map<String, Object> registration, String type) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", registration.get("email"));
        if (EasyStringUtil.isNotBlank(registration.get("verifiedMobile"))) {
            user.put("verifiedMobile", registration.get("verifiedMobile"));
        } else {
            user.put("verifiedMobile", "");
        }
        user.put("username", registration.get("username"));
        user.put("roles", EasyStringUtil.isBlank(registration.get("roles")) ? "ROLE_USER" :
                registration.get("roles"));
        user.put("type", type);
        user.put("createdIp", EasyStringUtil.isBlank(registration.get("createdIp")) ? "" :
                registration.get("createdIp"));
        user.put("createdTime", System.currentTimeMillis());

        if (ArrayUtil.inArray(type, "default", "phpwind", "discuz")) {
            BaseAppUser appUser = new BaseAppUser();
            appUser.setPassword(String.valueOf(registration.get("password")));
            userAccessControlService.setEncryptPassword(appUser);
            user.put("salt", appUser.getSalt());
            user.put("password", appUser.getPassword());
            user.put("setup", 1);
        } else {
            user.put("salt", "");
            user.put("password", "");
            user.put("setup", 0);
        }
        userDao.insert(user);
        return userDao.getByUsername(String.valueOf(user.get("username")));
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

    @Override
    public void updateUserRole(Integer id, String[] roles) {
        String toStringRoles = Arrays.stream(roles).collect(Collectors.joining("|"));
        Map<String, Object> model = new ParamMap().add("id", id).add("roles", toStringRoles).toMap();
        userDao.updateMap(model);
    }

    @Override
    public void updateUserProfile(Integer id, Map<String, Object> profile) {
        userProfileDao.updateOrInsert(id, profile);
    }
}
