package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;

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

    List<AppUser> searchUsers(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit);

    Map<String, Object> getProfile(Integer id);

    int countForEmail(String email);

    int updateMap(Map<String, Object> mapUser);

    int waveCounterById(Integer userId, String name, int number);

    int clearCounterById(Integer userId, String name);

    AppUser getByEmail(String email);

    List<Map<String, Object>> analysisRegisterDataByTime(long startTime, long endTime);
}
