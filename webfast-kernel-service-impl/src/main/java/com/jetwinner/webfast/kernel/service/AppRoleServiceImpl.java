package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.AppRoleDao;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;
import com.jetwinner.webfast.kernel.model.AppModelRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {

    private final AppRoleDao roleDao;

    public AppRoleServiceImpl(AppRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<AppModelRole> listAllRole() {
        return roleDao.listAll();
    }

    @Override
    public int searchRolesCount(Map<String, Object> conditions) {
        return roleDao.searchRolesCount(conditions);
    }

    @Override
    public List<AppModelRole> searchRoles(Map<String, Object> conditions, OrderByBuilder orderByBuilder, Integer start, Integer limit) {
        return roleDao.searchRoles(conditions, orderByBuilder, start, limit);
    }
}
