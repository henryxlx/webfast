package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserDao {

    AppUser getByUsername(String username);

    void insert(Map<String, Object> user);

    List<AppUser> findByIds(Set<Object> ids);

    AppUser getUser(Object id);

    int searchUserCount(Map<String, Object> conditions);

    List<AppUser> searchUsers(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit);

    Map<String, Object> getProfile(Integer id);

    int countForEmail(String email);
}
