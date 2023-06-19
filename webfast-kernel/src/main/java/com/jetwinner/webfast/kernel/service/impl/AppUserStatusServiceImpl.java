package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppUserStatusDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.service.AppUserStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
@Service
public class AppUserStatusServiceImpl implements AppUserStatusService {

    private final AppUserStatusDao statusDao;

    public AppUserStatusServiceImpl(AppUserStatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public Map<String, Object> publishStatus(Map<String, Object> status, Boolean deleteOld, AppUser currentUser) {
        if (EasyStringUtil.isBlank(status.get("userId"))) {

            if (currentUser == null || currentUser.getId() == null || currentUser.getId() == 0) {
                return null;
            }

            status.put("userId", currentUser.getId());
        }

        status.put("createdTime", System.currentTimeMillis());
        status.put("message", status.get("message") == null ? "" : status.get("message"));
        if (deleteOld) {
            this.deleteOldStatus(status);
        }

        return this.statusDao.addStatus(status);
    }

    private int deleteOldStatus(Map<String, Object> status) {
        if (EasyStringUtil.isNotBlank(status.get("userId")) &&
                EasyStringUtil.isNotBlank(status.get("type")) &&
                EasyStringUtil.isNotBlank(status.get("objectType")) &&
                EasyStringUtil.isNotBlank(status.get("objectId"))) {

            return this.statusDao.deleteStatusesByUserIdAndTypeAndObject(status.get("userId"), status.get("type"),
                    status.get("objectType"), status.get("objectId"));
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> findStatusesByUserIds(Set<Object> userIds, Integer start, Integer limit) {
        return this.statusDao.findStatusesByUserIds(userIds, start, limit);
    }

    @Override
    public List<Map<String, Object>> searchStatuses(Map<String, Object> conditions, OrderBy orderBy, Integer start, Integer limit) {
        return this.statusDao.searchStatuses(conditions, orderBy, start, limit);
    }
}
