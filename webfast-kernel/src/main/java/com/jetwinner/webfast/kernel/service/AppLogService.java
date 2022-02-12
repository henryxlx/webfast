package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppLogService {

    void info(AppUser currentUser, String module, String action, String message);

    void info(AppUser currentUser, String module, String action, String message, Map<String, Object> data);

    void warning(AppUser currentUser, String module, String action, String message);

    void warning(AppUser currentUser, String module, String action, String message, Map<String, Object> data);

    void error(AppUser currentUser, String module, String action, String message);

    void error(AppUser currentUser, String module, String action, String message, Map<String, Object> data);
}
