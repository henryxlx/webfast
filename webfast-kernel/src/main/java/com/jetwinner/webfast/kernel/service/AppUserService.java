package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.BaseAppUserService;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

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

    List<AppUser> searchUsers(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit);

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
}
