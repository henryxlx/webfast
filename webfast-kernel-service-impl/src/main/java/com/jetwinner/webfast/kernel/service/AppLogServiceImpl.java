package com.jetwinner.webfast.kernel.service;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppLogDao;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppLogServiceImpl implements AppLogService {

    private final UserAccessControlService accessControlService;
    private final AppLogDao logDao;

    public AppLogServiceImpl(UserAccessControlService accessControlService, AppLogDao logDao) {
        this.accessControlService = accessControlService;
        this.logDao = logDao;
    }

    @Override
    public void info(String module, String action, String message) {
        Object user = accessControlService.getCurrentUser();
        if (user != null && user instanceof AppUser) {
            info(module, action, message, (AppUser) user, null);
        } else {
            info(module, action, message, null, null);
        }
    }

    @Override
    public void info(String module, String action, String message, AppUser currentUser) {
        info(module, action, message, currentUser, null);
    }

    @Override
    public void info(String module, String action, String message, AppUser currentUser, Map<String, Object> data) {
        addLog("info", module, action, message, currentUser, data);
    }

    @Override
    public void warning(String module, String action, String message) {
        Object user = accessControlService.getCurrentUser();
        if (user != null && user instanceof AppUser) {
            warning(module, action, message, (AppUser) user, null);
        } else {
            warning(module, action, message, null, null);
        }
    }

    @Override
    public void warning(String module, String action, String message, AppUser currentUser) {
        warning(module, action, message, currentUser, null);
    }

    @Override
    public void warning(String module, String action, String message, AppUser currentUser, Map<String, Object> data) {
        addLog("warning", module, action, message, currentUser, data);
    }

    @Override
    public void error(String module, String action, String message) {
        Object user = accessControlService.getCurrentUser();
        if (user != null && user instanceof AppUser) {
            error(module, action, message, (AppUser) user, null);
        } else {
            error(module, action, message, null, null);
        }
    }

    @Override
    public void error(String module, String action, String message, AppUser currentUser) {
        error(module, action, message, currentUser, null);
    }

    @Override
    public void error(String module, String action, String message, AppUser currentUser, Map<String, Object> data) {
        addLog("error", module, action, message, currentUser, data);
    }

    private void addLog(String level, String module, String action, String message,
                        AppUser currentUser, Map<String, Object> data) {

        Map<String, Object> logModel = new ParamMap().add("module", module)
                .add("action", action).add("message", message)
                .add("data", data == null ? "" : JsonUtil.objectToString(data))
                .add("userId", currentUser != null ? currentUser.getId(): "lost")
                .add("ip", currentUser != null ? currentUser.getLoginIp(): "")
                .add("createdTime", System.currentTimeMillis())
                .add("level", level)
                .toMap();
        logDao.insertMap(logModel);
    }
}
