package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.util.ArrayUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppLogDao;
import com.jetwinner.webfast.kernel.dao.AppUserDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppLogService;
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
    private final AppUserDao userDao;

    public AppLogServiceImpl(AppLogDao logDao, AppUserDao userDao) {
        this.logDao = logDao;
        this.userDao = userDao;
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

        OrderBy orderBy;
        switch (sort) {
            case "created":
                orderBy = OrderBy.build(1).addDesc("createdTime");
                break;
            case "createdByAsc":
                orderBy = OrderBy.build(1).add("createdTime");
                break;
            default:
                throw new RuntimeGoingException("参数sort不正确。");
        }

        List<Map<String, Object>> logs = logDao.searchLogs(conditions, orderBy, start, limit);

        return logs;
    }

    @Override
    public List<Map<String, Object>> analysisLoginDataByTime(long startTime, long endTime) {
        return logDao.analysisLoginDataByTime(startTime, endTime);
    }

    private void prepareSearchConditions(Map<String, Object> conditions) {
        if (EasyStringUtil.isNotBlank(conditions.get("username"))) {
            AppUser existsUser = userDao.getByUsername(String.valueOf(conditions.get("username")));
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
