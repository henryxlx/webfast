package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserProfileDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Repository
public class AppUserProfileDaoImpl extends FastJdbcDaoSupport implements AppUserProfileDao {

    private static final String TABLE_NAME = "app_user_profile";

    @Override
    public void updateOrInsert(Integer id, Map<String, Object> profile) {
        verifyMapKeyForTableColumn(profile, TABLE_NAME);
        if (profile.isEmpty()) {
            return;
        }
        profile.put("id", id);
        List<Integer> list = getJdbcTemplate().queryForList(
                "SELECT 1 FROM app_user_profile WHERE id = ? LIMIT 1", Integer.class, id);

        int num = ListUtil.isEmpty(list) ? insertMap(TABLE_NAME, profile) :
                updateMap(TABLE_NAME, profile, "id");
    }

    @Override
    public void updateProfile(Map<String, Object> profile) {
        updateMap(TABLE_NAME, profile, "id");
    }

    @Override
    public List<Map<String, Object>> findProfilesByIds(Set<Object> ids) {
        if (ids == null || ids.size() < 1) {
            return new ArrayList<>(0);
        }
        String marks = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));
        String sql = String.format("SELECT * FROM %s WHERE id IN (%s);", TABLE_NAME, marks);
        return getJdbcTemplate().queryForList(sql);
    }
}
