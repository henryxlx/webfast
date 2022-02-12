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
    public void info(AppUser currentUser, String module, String action, String message) {
        info(currentUser, module, action, message, null);
    }

    @Override
    public void info(AppUser currentUser, String module, String action, String message, Map<String, Object> data) {
        addLog(currentUser, "info", module, action, message, data);
    }

    @Override
    public void warning(AppUser currentUser, String module, String action, String message) {
        warning(currentUser, module, action, message, null);
    }

    @Override
    public void warning(AppUser currentUser, String module, String action, String message, Map<String, Object> data) {
        addLog(currentUser, "warning", module, action, message, data);
    }

    @Override
    public void error(AppUser currentUser, String module, String action, String message) {
        error(currentUser, module, action, message, null);
    }

    @Override
    public void error(AppUser currentUser, String module, String action, String message, Map<String, Object> data) {
        addLog(currentUser, "error", module, action, message, data);
    }

    private void addLog(AppUser currentUser, String level, String module, String action, String message,
                        Map<String, Object> data) {

        Map<String, Object> logModel = new ParamMap().add("module", module)
                .add("action", action).add("message", message)
                .add("data", data == null ? "" : JsonUtil.objectToString(data))
                .add("userId", currentUser != null ? currentUser.getId() : "lost")
                .add("ip", currentUser != null ? currentUser.getLoginIp() : "")
                .add("createdTime", System.currentTimeMillis())
                .add("level", level)
                .toMap();
        logDao.insertMap(logModel);
    }
}
