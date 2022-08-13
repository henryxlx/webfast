package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.util.ArrayUtil;
import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppUserProfileDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
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

        int num = list.isEmpty() ? insertMap(TABLE_NAME, profile) :
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

    @Override
    public int dropFieldData(String fieldName) {
        String[] fieldNames = {
                "intField1",
                "intField2",
                "intField3",
                "intField4",
                "intField5",
                "dateField1",
                "dateField2",
                "dateField3",
                "dateField4",
                "dateField5",
                "floatField1",
                "floatField2",
                "floatField3",
                "floatField4",
                "floatField5",
                "textField1",
                "textField2",
                "textField3",
                "textField4",
                "textField5",
                "textField6",
                "textField7",
                "textField8",
                "textField9",
                "textField10",
                "varcharField1",
                "varcharField2",
                "varcharField3",
                "varcharField4",
                "varcharField5",
                "varcharField6",
                "varcharField7",
                "varcharField8",
                "varcharField9",
                "varcharField10"};
        if (!ArrayUtil.inArray(fieldName, fieldNames)) {
            throw new RuntimeGoingException(AppUserProfileDao.class + " DAO Exception fieldName error");
        }

        String sql= String.format("UPDATE %s set %s = null ", TABLE_NAME, fieldName);
        return getJdbcTemplate().update(sql);
    }
}
