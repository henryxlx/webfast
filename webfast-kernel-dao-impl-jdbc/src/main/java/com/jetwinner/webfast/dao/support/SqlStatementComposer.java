package com.jetwinner.webfast.dao.support;

import com.jetwinner.util.EasyStringUtil;

import java.util.Map;

/**
 * @author xulixin
 */
public class SqlStatementComposer {

    private SqlStatementComposer() {
        // reserved.
    }

    public static String getInsertSql(String tableName, Map<String, ?> mapFromBean) {
        return getInsertSql(tableName, mapFromBean.keySet().toArray(new String[0]));
    }

    public static String getInsertSql(String tableName, String[] keys) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append("(");
        int len = keys.length;
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(keys[i]);
        }
        sql.append(") VALUES (");
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(":").append(keys[i]);
        }
        sql.append(")");
        return sql.toString();
    }

    public static String getUpdateSql(String tableName, Map<String, ?> mapFromBean, String... primaryKeyNames) {
        return getUpdateSql(tableName, mapFromBean.keySet().toArray(new String[0]), primaryKeyNames);
    }

    public static String getUpdateSql(String tableName, String[] columnNames, String[] primaryKeyNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName).append(" SET ");
        int count = 0;
        for (String columnName : columnNames) {
            if (columnName == null || EasyStringUtil.inArray(columnName, primaryKeyNames)) {
                continue;
            }
            sql.append(count > 0 ? ", " : "");
            sql.append(columnName).append(" = :").append(columnName);
            count++;
        }
        sql.append(" WHERE ");
        for (int len = primaryKeyNames.length, i = 0; i < len; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(primaryKeyNames[i]).append(" = :").append(primaryKeyNames[i]);
        }
        return sql.toString();
    }
}
