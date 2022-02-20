package com.jetwinner.webfast.dao.support;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author xulixin
 */
public class FastJdbcDaoSupport extends NamedParameterJdbcDaoSupport {

    @Autowired
    public final void setSuperDataSource(DataSource dataSource) {
        setDataSource(dataSource);
    }

    private final Set<String> tableColumns = new HashSet<>();

    protected Set<String> getTableColumns(String tableName) {
        if (this.tableColumns.size()== 0) {
            String[] strArray = getJdbcTemplate().queryForRowSet("SELECT * FROM "+ tableName + " LIMIT 1").getMetaData().getColumnNames();
            for (String str : strArray) {
                tableColumns.add(str);
            }
        }
        return tableColumns;
    }

    protected void containColumnBySet(Map<String, Object> profile, Set<String> columns) {
        Iterator<Map.Entry<String, Object>> it = profile.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            if (!columns.contains(key)) {
                it.remove();
            }
        }
    }

    protected void verifyMapKeyForTableColumn(Map<String, Object> mapForTable, String tableName) {
        containColumnBySet(mapForTable, getTableColumns(tableName));
    }

    protected <K, V> Map<K, V> toEmptyMap() {
        return new HashMap<>(0);
    }

    protected <T> int insert(String tableName, T anyBean) {
        BeanPropertySqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(anyBean);
        String sql = SqlStatementComposer.getInsertSql(tableName, sqlParameterSource.getParameterNames());
        return getNamedParameterJdbcTemplate().update(sql, sqlParameterSource);
    }

    protected <T> Number insertReturnKey(String tableName, T anyBean) {
        BeanPropertySqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(anyBean);
        String sql = SqlStatementComposer.getInsertSql(tableName, sqlParameterSource.getParameterNames());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, sqlParameterSource, keyHolder);
        return keyHolder.getKey();
    }

    protected int insertMap(String tableName, Map<String, Object> map) {
        if (map == null || map.size() < 1) {
            return 0;
        }
        String sql = SqlStatementComposer.getInsertSql(tableName, map);
        return getNamedParameterJdbcTemplate().update(sql, map);
    }

    protected Number insertMapReturnKey(String tableName, Map<String, Object> map) {
        if (map == null || map.size() < 1) {
            return 0;
        }
        String sql = SqlStatementComposer.getInsertSql(tableName, map);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey();
    }

    protected <T> int update(String tableName, T anyBean, String... primaryKeyNames) {
        BeanPropertySqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(anyBean);
        String sql = SqlStatementComposer.getUpdateSql(tableName, sqlParameterSource.getParameterNames(), primaryKeyNames);
        return getNamedParameterJdbcTemplate().update(sql, sqlParameterSource);
    }

    protected int updateMap(String tableName, Map<String, Object> map, String primaryKeyName) {
        return updateMap(tableName, map, primaryKeyName, null);
    }

    protected int updateMap(String tableName, Map<String, Object> map, String primaryKeyName, Object pkValue) {
        if (map == null || map.size() < 1 || EasyStringUtil.isBlank(primaryKeyName)) {
            return 0;
        }

        if (pkValue != null) {
            map.put(primaryKeyName, pkValue);
        }

        String sql = SqlStatementComposer.getUpdateSql(tableName, map, primaryKeyName);
        return getNamedParameterJdbcTemplate().update(sql, map);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        List<T> results = getJdbcTemplate().query(sql, rowMapper, args);
        return results.stream().findFirst().orElse(null);
    }

    private int deleteByPrimaryKey(String tableName, String primaryKeyName, Object pkValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName);
        sql.append(" WHERE ").append(primaryKeyName).append(" = ?");
        return getJdbcTemplate().update(sql.toString(), pkValue);
    }

    protected void checkOrderBy(OrderBy orderBy, String[] allowedOrderByFields) {
        if (EasyStringUtil.isBlank(orderBy.getColumnName()) || EasyStringUtil.isBlank(orderBy.getSortType())) {
            throw new RuntimeException("orderBy参数不正确");
        }
        if (!EasyStringUtil.inArray(orderBy.getColumnName(), allowedOrderByFields)) {
            throw new RuntimeException(String.format("不允许对%s字段进行排序", orderBy.getColumnName()));
        }
        if (!EasyStringUtil.inArray(orderBy.getSortType(), new String[]{"ASC", "DESC"})) {
            throw new RuntimeException("orderBy排序方式错误");
        }
    }
}