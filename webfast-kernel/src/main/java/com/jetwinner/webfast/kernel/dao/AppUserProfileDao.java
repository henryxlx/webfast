package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserProfileDao {

    void updateOrInsert(Integer id, Map<String, Object> profile);

    void updateProfile(Map<String, Object> profile);

    List<Map<String, Object>> findProfilesByIds(Set<Object> ids);
}
