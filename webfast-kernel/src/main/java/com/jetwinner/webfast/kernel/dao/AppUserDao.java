package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserDao {

    AppUser getByUsername(String username);

    void insert(Map<String, Object> user);
}
