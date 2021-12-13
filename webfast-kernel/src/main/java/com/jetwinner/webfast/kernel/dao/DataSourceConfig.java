package com.jetwinner.webfast.kernel.dao;

/**
 * @author xulixin
 */
public interface DataSourceConfig {

    String JDBC_URL_MYSQL_HOST_FORMATTER = "jdbc:mysql://%s:%s";
    String JDBC_URL_DATABASE_SETTING_FORMATTER = "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    static String getMysqlJdbcUrl(String dbHost, String dbPort) {
        return getMysqlJdbcUrl(dbHost, dbPort, null);
    }

    static String getMysqlJdbcUrl(String dbHost, String dbPort, String dbName) {
        StringBuilder buf = new StringBuilder();
        buf.append(JDBC_URL_MYSQL_HOST_FORMATTER);
        if (dbName != null) {
            buf.append("/%s");
        }
        buf.append(JDBC_URL_DATABASE_SETTING_FORMATTER);
        return String.format(buf.toString(), dbHost, dbPort, dbName);
    }

    boolean getDataSourceDisabled();

    void setDataSourceDisabled(boolean dataSourceDisabled);
}
