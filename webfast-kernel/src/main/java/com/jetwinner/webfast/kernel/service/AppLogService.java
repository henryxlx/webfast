package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppLogService {

    public void info(String module, String action, String message);

    void info(String module, String action, String message, AppUser currentUser);

    public void info(String module, String action, String message, AppUser currentUser, Map<String, Object> data);

    void warning(String module, String action, String message);

    void warning(String module, String action, String message, AppUser currentUser);

    void warning(String module, String action, String message, AppUser currentUser, Map<String, Object> data);

    void error(String module, String action, String message);

    void error(String module, String action, String message, AppUser currentUser);

    void error(String module, String action, String message, AppUser currentUser, Map<String, Object> data);
}
