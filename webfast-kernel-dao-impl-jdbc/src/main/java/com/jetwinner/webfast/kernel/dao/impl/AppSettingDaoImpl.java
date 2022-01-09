package com.jetwinner.webfast.kernel.dao.impl;

import com.jetwinner.webfast.dao.support.FastJdbcDaoSupport;
import com.jetwinner.webfast.kernel.dao.AppSettingDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

/**
 * @author xulixin
 */
@Repository
public class AppSettingDaoImpl extends FastJdbcDaoSupport implements AppSettingDao {

    @Override
    public int deleteSettingByName(String name) {
        return getJdbcTemplate().update("DELETE FROM app_setting WHERE name = ?", name);
    }

    @Override
    public void updateSetting(String name, Map<String, Object> mapForSettingValue) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("name", name);
        byte[] settingBlobData = SerializationUtils.serialize(mapForSettingValue);
        in.addValue("value",  new SqlLobValue(new ByteArrayInputStream(settingBlobData),
                settingBlobData.length, new DefaultLobHandler()), Types.BLOB);

        String updateSql = "UPDATE app_setting set value = :value where name = :name";
        getNamedParameterJdbcTemplate().update(updateSql, in);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getSettingValueByName(String name) {
        final LobHandler lobHandler = new DefaultLobHandler();
        final Object[] result = new Object[1];
        getJdbcTemplate().query(
                "SELECT value FROM app_setting WHERE name = ?", new Object[] {name},
                new AbstractLobStreamingResultSetExtractor<Object>() {
                    @Override
                    public void streamData(ResultSet rs) throws SQLException, IOException {
                        result[0] = SerializationUtils.deserialize(lobHandler.getBlobAsBytes(rs, 1));
                    }
                }
        );
        return (Map<String, Object>) result[0];
    }
}
