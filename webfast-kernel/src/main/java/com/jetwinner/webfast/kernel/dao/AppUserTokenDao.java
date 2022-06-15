package com.jetwinner.webfast.kernel.dao;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserTokenDao {

    void addToken(Map<String, Object> entityMap);

    Map<String, Object> findTokenByToken(String token);

    int deleteToken(Object id);
}
