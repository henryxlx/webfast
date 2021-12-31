package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppUserDao {

    AppUser getByUsername(String username);

    void insert(Map<String, Object> user);

    List<Map<String, Object>> findByIds(Set<Object> ids);

    AppUser getUser(Object id);
}
