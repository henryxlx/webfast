package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.BaseAppUserService;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserService extends BaseAppUserService {

    AppUser getUserByUsername(String username);

    AppUser register(Map<String, Object> user);

    Map<String, AppUser> findUsersByIds(Set<Object> userIds);

    AppUser getUser(Object id);

    int searchUserCount(Map<String, Object> conditions);

    List<AppUser> searchUsers(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit);

    Map<String, Object> getUserProfile(Integer id);

    boolean existEmail(String value);

    void changeUserRoles(Integer id, String... roles);

    void updateUserProfile(Integer userId, Map<String, Object> profile);

    void changePassword(Integer id, String oldPassword, String newPassword);

    void lockUser(Integer id);

    void unlockUser(Integer id);

    boolean changeAvatar(Integer id, String filePath, Map<String, Object> options);

    void waveUserCounter(Integer userId, String name, int number);

    void clearUserCounter(Integer userId, String name);

    boolean isUsernameAvailable(String username);

    Set<Object> filterFollowingIds(Integer userId, Set<Object> followingUserIds);

    void applyUserApproval(Integer userId, Map<String, Object> approvalMap,
                           String faceImgPath, String backImgPath, String directory);

    List<Map<String, Object>> findUserApprovalsByUserIds(Set<Object> userIds);

    List<Map<String, Object>> findUserProfilesByIds(Set<Object> ids);

    Map<String, Object> getLastestApprovalByUserIdAndStatus(Integer userId, String status);

    void passApproval(Integer userId, String note, AppUser currentUser);

    void rejectApproval(Integer userId, String note, AppUser currentUser);

    List<Map<String, Object>> getUserSecureQuestionsByUserId(Object userId);

    void addUserSecureQuestionsWithUnHashedAnswers(AppUser currentUser, Map<String, Object> fields);

    AppUser getUserByEmail(String email);

    String makeToken(String type, Integer userId, long expiredTime, Object data);
    String makeToken(String type, Integer userId, long expiredTime);

    void rememberLoginSessionId(Integer userId, String sessionId);

    Map<String, Object> getToken(String type, String token);

    boolean deleteToken(String type, String token);

    void dropFieldData(Object fieldName);

    List<Map<String, Object>> analysisRegisterDataByTime(long startTime, long endTime);

    List<Map<String, Object>> analysisUserSumByTime(long endTime);
}
