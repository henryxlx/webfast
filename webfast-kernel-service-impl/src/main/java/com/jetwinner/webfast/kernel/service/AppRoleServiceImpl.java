package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.EasyStringUtil;
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

    @Override
    public void addRole(Map<String, Object> mapRole) {
        roleDao.insert(mapRole);
    }

    @Override
    public boolean isRoleNameAvailable(String roleName, String exclude) {
        if (EasyStringUtil.isBlank(roleName)) {
            return false;
        }
        if (roleName.equals(exclude)) {
            return true;
        }

        int count = roleDao.countByRoleName(roleName);
        return count > 0 ? false : true;
    }

    @Override
    public int deleteRoleById(Integer id) {
        return roleDao.deleteById(id);
    }

    @Override
    public AppModelRole getRoleById(Integer id) {
        return roleDao.getById(id);
    }

    @Override
    public Map<String, Object> getRoleMapById(Integer id) {
        return roleDao.getRoleMapById(id);
    }

    @Override
    public boolean update(Map<String, Object> mapForNew) {
        return roleDao.updateMap(mapForNew) > 0;
    }
}
