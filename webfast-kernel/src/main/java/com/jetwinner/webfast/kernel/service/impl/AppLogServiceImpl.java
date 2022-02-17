package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.util.ArrayUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppLogDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppLogServiceImpl implements AppLogService {

    private final AppLogDao logDao;
    private final AppUserService userService;

    public AppLogServiceImpl(AppLogDao logDao, AppUserService userService) {
        this.logDao = logDao;
        this.userService = userService;
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

    @Override
    public int searchLogCount(Map<String, Object> conditions) {
        prepareSearchConditions(conditions);
        return logDao.searchLogCount(conditions);
    }

    @Override
    public List<Map<String, Object>> searchLogs(Map<String, Object> conditions, String sort, Integer start, Integer limit) {
        prepareSearchConditions(conditions);

        OrderByBuilder orderByBuilder;
        switch (sort) {
            case "created":
                orderByBuilder = new OrderByBuilder().addDesc("createdTime");
                break;
            case "createdByAsc":
                orderByBuilder = new OrderByBuilder().add("createdTime");
                break;

            default:
                throw new RuntimeGoingException("参数sort不正确。");
        }

        List<Map<String, Object>> logs = logDao.searchLogs(conditions, orderByBuilder, start, limit);

        return logs;
    }

    private void prepareSearchConditions(Map<String, Object> conditions) {
        if (EasyStringUtil.isNotBlank(conditions.get("username"))) {
            AppUser existsUser = userService.getUserByUsername(String.valueOf(conditions.get("username")));
            Integer userId = existsUser != null ? existsUser.getId() : -1;
            conditions.put("userId", userId);
            conditions.remove("username");
        }

        if (EasyStringUtil.isNotBlank(conditions.get("startDateTime")) &&
                EasyStringUtil.isNotBlank(conditions.get("endDateTime"))) {
            // do nothing.
        } else {
            conditions.remove("startDateTime");
            conditions.remove("endDateTime");
        }

        if (EasyStringUtil.isNotBlank(conditions.get("level")) &&
                ArrayUtil.inArray(conditions.get("level"), "info", "warning", "error")) {
            // do nothing.
        } else {
            conditions.remove("level");
        }
    }
}
