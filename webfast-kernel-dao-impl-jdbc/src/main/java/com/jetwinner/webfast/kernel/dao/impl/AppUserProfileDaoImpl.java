package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserProfileDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppUserProfileDaoImpl extends FastJdbcDaoSupport implements AppUserProfileDao {

    private String TABLE_NAME = "app_user_profile";

    @Override
    public void updateOrInsert(Integer id, Map<String, Object> profile) {
        profile.put("id", id);
        verifyMapKeyForTableColumn(profile, TABLE_NAME);
        List<Integer> list = getJdbcTemplate().queryForList(
                "SELECT 1 FROM app_user_profile WHERE id = ? LIMIT 1", Integer.class, id);

        int num = ListUtil.isEmpty(list) ? insertMap(TABLE_NAME, profile) :
                updateMap(TABLE_NAME, profile, "id");
    }
}
