package com.jetwinner.webfast.kernel.dao;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserProfileDao {

    void updateOrInsert(Integer id, Map<String, Object> profile);
}
