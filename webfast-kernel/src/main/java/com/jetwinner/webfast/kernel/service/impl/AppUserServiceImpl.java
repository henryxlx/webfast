package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.security.RbacService;
import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.security.UserHasRoleAndPermission;
import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.util.*;
import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.image.ImageUtil;
import com.jetwinner.webfast.kernel.AppRole;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.dao.*;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.ActionGraspException;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppPathInfo;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.service.AppNotificationService;
import com.jetwinner.webfast.kernel.service.AppUserService;
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
    private final AppUserApprovalDao userApprovalDao;
    private final AppUserProfileDao userProfileDao;
    private final AppRoleDao roleDao;
    private final AppFriendDao friendDao;
    private final AppNotificationService notificationService;
    private final DataSourceConfig dataSourceConfig;
    private final RbacService rbacService;
    private final FastAppConst appConst;
    private final UserAccessControlService userAccessControlService;
    private final AppLogService logService;
    private final AppUserTokenDao userTokenDao;

    public AppUserServiceImpl(AppUserDao userDao,
                              AppUserApprovalDao userApprovalDao,
                              AppUserProfileDao userProfileDao,
                              AppRoleDao roleDao,
                              AppFriendDao friendDao,
                              AppNotificationService notificationService,
                              DataSourceConfig dataSourceConfig,
                              RbacService rbacService,
                              FastAppConst appConst,
                              UserAccessControlService userAccessControlService,
                              AppLogService logService, AppUserTokenDao userTokenDao) {

        this.userDao = userDao;
        this.userApprovalDao = userApprovalDao;
        this.userProfileDao = userProfileDao;
        this.roleDao = roleDao;
        this.friendDao = friendDao;
        this.notificationService = notificationService;
        this.dataSourceConfig = dataSourceConfig;
        this.rbacService = rbacService;
        this.appConst = appConst;
        this.userAccessControlService = userAccessControlService;
        this.logService = logService;
        this.userTokenDao = userTokenDao;
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
        List<AppRole> roles = roleDao.listAll().stream()
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
    public List<AppUser> searchUsers(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit) {
        return userDao.searchUsers(conditions, orderBy, start, limit);
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
    public void changeUserRoles(Integer id, String... roles) {
        if (roles == null || roles.length == 0) {
            return;
        }
        String toStringRoles = String.join("|", roles);
        Map<String, Object> model = new ParamMap().add("id", id).add("roles", toStringRoles).toMap();
        userDao.updateMap(model);
    }

    @Override
    public void updateUserProfile(Integer id, Map<String, Object> profile) {
        if (EasyStringUtil.isNotBlank(profile.get("title"))) {
            Map<String, Object> fields = new ParamMap().add("id", id).add("title", profile.get("title")).toMap();
            userDao.updateMap(fields);
        }
        userProfileDao.updateOrInsert(id, profile);
    }

    @Override
    public void changePassword(Integer id, String oldPassword, String newPassword) {
        AppUser user = getUser(id);
        if (user == null || EasyStringUtil.isBlank(newPassword)) {
            throw new RuntimeGoingException("参数不正确，更改密码失败。");
        }

        BaseAppUser userForPassword = new BaseAppUser();
        userForPassword.setPassword(newPassword);
        userAccessControlService.setEncryptPassword(userForPassword);

        Map<String, Object> fields = new ParamMap().add("id", id)
                .add("salt", userForPassword.getSalt())
                .add("password", userForPassword.getPassword()).toMap();
        userDao.updateMap(fields);

        // logService.info("user", "password-changed", String.format("用户{%s}(ID:{%d})重置密码成功", user.getEmail(), user.getId()));
    }

    @Override
    public void lockUser(Integer id) {
        AppUser user = getUser(id);
        if (user == null) {
            throw new RuntimeGoingException("用户不存在，封禁失败！");
        }
        userDao.updateMap(new ParamMap().add("id", id).add("locked", 1).toMap());

        // logService.info("user", "lock", String.format("封禁用户{%s}(#{%d})", user.getUsername(), user.getId()));
    }

    @Override
    public void unlockUser(Integer id) {
        AppUser user = getUser(id);
        if (user == null) {
            throw new RuntimeGoingException("用户不存在，解禁失败！");
        }
        userDao.updateMap(new ParamMap().add("id", id).add("locked", 0).toMap());

        // logService.info("user", "unlock", String.format("解禁用户{%s}(#{%d})", user.getUsername(), user.getId()));
    }

    @Override
    public boolean changeAvatar(Integer userId, String filePath, Map<String, Object> options) {
        AppUser user = getUser(userId);
        if (user == null) {
            throw new RuntimeGoingException("用户不存在，头像更新失败！");
        }

        ParamMap paramMap = new ParamMap().add("id", userId);

        AppPathInfo pathInfo = new AppPathInfo(filePath, "!");

        String cropImageFilePath = String.format("%s/%s_crop.%s",
                pathInfo.getDirname(), pathInfo.getFilename(), pathInfo.getExtension());
        int x = (int)ValueParser.parseFloat(options.get("x"));
        int y = (int)ValueParser.parseFloat(options.get("y"));
        int width = (int)ValueParser.parseFloat(options.get("width"));
        int height = (int)ValueParser.parseFloat(options.get("height"));
        ImageUtil.cropPartImage(filePath, cropImageFilePath, pathInfo.getExtension(), x, y, width, height);

        Calendar now = Calendar.getInstance();
        String userUploadPath = String.format("user/%d/%02d-%02d", now.get(Calendar.YEAR),
                now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
        String userUploadRealDir = appConst.getUploadPublicDirectory() + "/" + userUploadPath;
        if (FastDirectoryUtil.dirNotExists(userUploadRealDir)) {
            FastDirectoryUtil.makeDir(userUploadRealDir);
        }
        String largeFilePath = String.format("%s/%s_large.%s",
                userUploadRealDir, pathInfo.getFilename(), pathInfo.getExtension());
        try {
            ImageUtil.resizeImage(cropImageFilePath, largeFilePath, pathInfo.getExtension(), 200, 0.9f);
        } catch (Exception e) {
            throw new RuntimeGoingException("Resize large user image error: " + e.getMessage());
        }
        paramMap.add("largeAvatar", String.format("public://%s/%s_large.%s",
                userUploadPath, pathInfo.getFilename(), pathInfo.getExtension()));


        String mediumFilePath = String.format("%s/%s_medium.%s",
                userUploadRealDir, pathInfo.getFilename(), pathInfo.getExtension());
        try {
            ImageUtil.resizeImage(cropImageFilePath, mediumFilePath, pathInfo.getExtension(), 120, 0.9f);
        } catch (Exception e) {
            throw new RuntimeGoingException("Resize medium user image error: " + e.getMessage());
        }
        paramMap.add("mediumAvatar", String.format("public://%s/%s_medium.%s",
                userUploadPath, pathInfo.getFilename(), pathInfo.getExtension()));

        String smallFilePath = String.format("%s/%s_small.%s",
                userUploadRealDir, pathInfo.getFilename(), pathInfo.getExtension());
        try {
            ImageUtil.resizeImage(cropImageFilePath, smallFilePath, pathInfo.getExtension(),48, 0.9f);
        } catch (Exception e) {
            throw new RuntimeGoingException("Resize small user image error: " + e.getMessage());
        }
        paramMap.add("smallAvatar", String.format("public://%s/%s_small.%s",
                userUploadPath, pathInfo.getFilename(), pathInfo.getExtension()));

        return userDao.updateMap(paramMap.toMap()) > 0;
    }

    @Override
    public void waveUserCounter(Integer userId, String name, int number) {
        userDao.waveCounterById(userId, name, number);
    }

    @Override
    public void clearUserCounter(Integer userId, String name) {
        userDao.clearCounterById(userId, name);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        if (EasyStringUtil.isBlank(username)) {
            return false;
        }
        AppUser user = userDao.getByUsername(username);
        return null == user ? false : true;
    }

    @Override
    public Set<Object> filterFollowingIds(Integer userId, Set<Object> followingUserIds) {
        if (EasyStringUtil.isNotBlank(followingUserIds)) {
            return new HashSet<>(0);
        }
        List<Map<String, Object>> friends = friendDao.getFriendsByFromIdAndToIds(userId, followingUserIds);
        return ArrayToolkit.column(friends, "toId");
    }

    @Override
    public void applyUserApproval(Integer userId, Map<String, Object> approvalMap,
                                  String faceImgPath, String backImgPath, String directory) {

        approvalMap.put("userId", userId);
        approvalMap.put("faceImg", new AppPathInfo(faceImgPath).getDirname());
        approvalMap.put("backImg", new AppPathInfo(backImgPath).getDirname());
        approvalMap.put("status", "approving");
        approvalMap.put("createdTime", System.currentTimeMillis());

        userDao.updateMap(new ParamMap()
                        .add("approvalStatus", "approving")
                        .add("approvalTime", System.currentTimeMillis()).add("id", userId).toMap());

        userApprovalDao.addApproval(approvalMap);
    }

    @Override
    public List<Map<String, Object>> findUserApprovalsByUserIds(Set<Object> userIds) {
        return userApprovalDao.findApprovalsByUserIds(userIds);
    }

    @Override
    public List<Map<String, Object>> findUserProfilesByIds(Set<Object> ids) {
        return userProfileDao.findProfilesByIds(ids);
    }

    @Override
    public Map<String, Object> getLastestApprovalByUserIdAndStatus(Integer userId, String status) {
        return userApprovalDao.getLastestApprovalByUserIdAndStatus(userId, status);
    }

    @Override
    public void passApproval(Integer userId, String note, AppUser currentUser) {
        AppUser user = userDao.getUser(userId);
        if (user == null) {
            throw new RuntimeGoingException(String.format("用户#%d不存在！", userId));
        }

        userDao.updateMap(new ParamMap()
                .add("id", user.getId())
                .add("approvalStatus", "approved")
                .add("approvalTime", System.currentTimeMillis()).toMap());

        Map<String, Object> lastestApproval = userApprovalDao.getLastestApprovalByUserIdAndStatus(user.getId(), "approving");

        userProfileDao.updateProfile(new ParamMap()
                .add("id", userId)
                .add("truename", lastestApproval.get("truename"))
                .add("idcard", lastestApproval.get("idcard")).toMap());

        logService.info(currentUser, "user", "approved",
                String.format("用户%s实名认证成功，操作人:%s !", user.getUsername(), currentUser.getUsername()));

        String message = "您的个人实名认证，审核已经通过！" + (EasyStringUtil.isNotBlank(note) ? "(" + note + ")" : "");
        notificationService.notify(user.getId(), "default", message);
    }

    @Override
    public void rejectApproval(Integer userId, String note, AppUser currentUser) {
        AppUser user = userDao.getUser(userId);
        if (user == null) {
            throw new RuntimeGoingException(String.format("用户#%d不存在！", userId));
        }

        userDao.updateMap(new ParamMap()
                .add("id", user.getId())
                .add("approvalStatus", "approve_fail")
                .add("approvalTime", System.currentTimeMillis()).toMap());

        userApprovalDao.addApproval(new ParamMap()
                .add("userId", user.getId()).add("note", note)
                .add("status", "approve_fail").add("operatorId", currentUser.getId()).toMap());

        logService.info(currentUser, "user", "approval_fail",
                String.format("用户%s实名认证失败，操作人:%s !", user.getUsername(), currentUser.getUsername()));

        String message = "您的个人实名认证，审核未通过！" + (note != null ? "(" + note + ")" : "");
        notificationService.notify(user.getId(), "default", message);
    }

    @Override
    public List<Map<String, Object>> getUserSecureQuestionsByUserId(Object userId) {
        return null;
    }

    @Override
    public void addUserSecureQuestionsWithUnHashedAnswers(AppUser currentUser, Map<String, Object> fields) {

    }

    @Override
    public AppUser getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public String makeToken(String type, Integer userId, long expiredTime, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("userId", userId == null ? 0 : userId);
        // map.put("token", base_convert(sha1(uniqid(mt_rand(), true)), 16, 36);
        String token = TokenProducer.getInstance().generateToken();
        map.put("token", token);
        map.put("data", JsonUtil.objectToString(data == null ? "" : data));
        map.put("expiredTime", expiredTime);
        map.put("createdTime", System.currentTimeMillis());
        userTokenDao.addToken(map);
        return token;
    }

    @Override
    public String makeToken(String type, Integer userId, long expiredTime) {
        return makeToken(type, userId, expiredTime, null);
    }

    @Override
    public void rememberLoginSessionId(Integer userId, String sessionId) {
        userDao.updateMap(new ParamMap().add("id", userId).add("loginSessionId", sessionId).toMap());
    }

    @Override
    public Map<String, Object> getToken(String type, String token) {
        Map<String, Object> tokenMap = userTokenDao.findTokenByToken(token);
        if (tokenMap == null || !type.equals(tokenMap.get("type"))) {
            return null;
        }
        long expiredTime = ValueParser.parseLong(tokenMap.get("expiredTime"));
        if (expiredTime > 0 && expiredTime < System.currentTimeMillis()) {
            return null;
        }
        // tokenMap.put("data", unserialize(tokenMap.get("data")));
        return tokenMap;
    }

    @Override
    public boolean deleteToken(String type, String token) {
        Map<String, Object> tokenMap = userTokenDao.findTokenByToken(token);
        if (token == null || !type.equals(tokenMap.get("type"))) {
            return false;
        }
        userTokenDao.deleteToken(tokenMap.get("id"));
        return true;
    }

    @Override
    public void dropFieldData(Object fieldName) {
        userProfileDao.dropFieldData(String.valueOf(fieldName));
    }

    @Override
    public List<Map<String, Object>> analysisRegisterDataByTime(long startTime, long endTime) {
        return userDao.analysisRegisterDataByTime(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> analysisUserSumByTime(long endTime) {
        List<Map<String, Object>> perDayUserAddCount = userDao.analysisUserSumByTime(endTime);
        List<Map<String, Object>> dayUserTotals = new ArrayList<>();
        for (int key = 0; key < perDayUserAddCount.size(); key++) {
            Map<String, Object> model = new HashMap<>();
            model.put("date", perDayUserAddCount.get(key).get("date"));
            model.put("count", 0);
            dayUserTotals.add(model);
            int count = ValueParser.parseInt(dayUserTotals.get(key).get("count"));
            for (int i = key; i < perDayUserAddCount.size(); i++) {
                count += ValueParser.parseInt(perDayUserAddCount.get(i).get("count"));
            }
            model.put("count", count);
        }
        return dayUserTotals;
    }

    @Override
    public Map<String, Object> follow(Integer fromId, Integer toId) throws ActionGraspException {
        AppUser fromUser = this.getUser(fromId);
        AppUser toUser = this.getUser(toId);
        if (fromUser == null || toUser == null) {
            throw new ActionGraspException("用户不存在，关注失败！");
        }
        if (fromId.intValue() == toId.intValue()) {
            throw new ActionGraspException("不能关注自己！");
        }
        Map<String, Object> friend = this.friendDao.getFriendByFromIdAndToId(fromId, toId);
        if (MapUtil.isNotEmpty(friend)) {
            throw new ActionGraspException("不允许重复关注!");
        }
        Map<String, Object> fields = new ParamMap()
                .add("fromId", fromId)
                .add("toId", toId)
                .add("createdTime", System.currentTimeMillis()).toMap();
        return this.friendDao.addFriend(fields);
    }

    @Override
    public int unFollow(Integer fromId, Integer toId) throws ActionGraspException {
        AppUser fromUser = this.getUser(fromId);
        AppUser toUser = this.getUser(toId);
        if (fromUser == null || toUser == null) {
            throw new ActionGraspException("用户不存在，取消关注失败！");
        }
        Map<String, Object> friend = this.friendDao.getFriendByFromIdAndToId(fromId, toId);
        if (MapUtil.isEmpty(friend)) {
            throw new ActionGraspException("不存在此关注关系，取消关注失败！");
        }
        return this.friendDao.deleteFriend(friend.get("id"));
    }

    @Override
    public Map<String, AppUser> findAllUserFollowing(Integer userId) {
        List<Map<String, Object>> friends = this.friendDao.findAllUserFollowingByFromId(userId);
        Set<Object> ids = ArrayToolkit.column(friends, "toId");
        return this.findUsersByIds(ids);
    }

    @Override
    public Map<String, AppUser> findAllUserFollower(Integer userId) {
        List<Map<String, Object>> friends = this.friendDao.findAllUserFollowerByToId(userId);
        Set<Object> ids = ArrayToolkit.column(friends, "fromId");
        return this.findUsersByIds(ids);
    }

    @Override
    public Boolean isFollowed(Integer fromId, Integer toId) {
        AppUser fromUser = this.getUser(fromId);
        AppUser toUser = this.getUser(toId);
        if (fromUser == null) {
            throw new RuntimeGoingException("用户不存在，检测关注状态失败！");
        }

        if (toUser == null) {
            throw new RuntimeGoingException("被关注者不存在，检测关注状态失败！");
        }

        Map<String, Object> friend = this.friendDao.getFriendByFromIdAndToId(fromId, toId);
        return MapUtil.isEmpty(friend) ? Boolean.FALSE : Boolean.TRUE;
    }
}
