package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppPermissionDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppPermissionDaoImpl extends FastJdbcDaoSupport implements AppPermissionDao {

    @Override
    public List<Map<String, Object>> findAll() {
        return getJdbcTemplate().queryForList("SELECT * FROM app_permission");
    }

    @Override
    public void insert(Map<String, Object> mapPermission) {
        Long now = System.currentTimeMillis();
        mapPermission.put("createdTime", now);
        mapPermission.put("updatedTime", now);
        Integer id = insertMapReturnKey("app_permission", mapPermission).intValue();
        mapPermission.put("id", id);
    }

    @Override
    public boolean isPermissionKeyAvailable(String permissionKey, String exclude) {
        if (EasyStringUtil.isBlank(permissionKey)) {
            return false;
        }
        if (permissionKey.equals(exclude)) {
            return true;
        }

        int count = countByRoleName(permissionKey);
        return count > 0 ? false : true;
    }

    @Override
    public int delete(Integer id) {
        return getJdbcTemplate().update("DELETE FROM app_permission WHERE id = ?", id);
    }

    @Override
    public Map<String, Object> get(Integer id) {
        return getJdbcTemplate().queryForList("SELECT * FROM app_permission WHERE id = ?", id)
                .stream().findFirst().orElse(new HashMap<>(0));
    }

    @Override
    public void update(Map<String, Object> mapPermission) {
        Long now = System.currentTimeMillis();
        mapPermission.put("updatedTime", now);
        updateMap("app_permission", mapPermission, "id");
    }

    private int countByRoleName(String permissionKey) {
        return getJdbcTemplate().queryForObject("SELECT count(id) FROM app_permission WHERE permissionKey = ?",
                Integer.class, permissionKey);
    }
}
