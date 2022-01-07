package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserService {

    void checkPutMeIntoShrioAccountService();

    AppUser getByUsername(String username);

    Set<String> findRolesByUsername(String username);

    Set<String> findPermissionsByUsername(String username);

    void register(Map<String, Object> user);

    Map<String, AppUser> findUsersByIds(Set<Object> userIds);

    AppUser getUser(Object id);

    int searchUserCount(Map<String, Object> conditions);

    List<AppUser> searchUsers(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit);

    Map<String, Object> getUserProfile(Integer id);

    boolean existEmail(String value);
}
